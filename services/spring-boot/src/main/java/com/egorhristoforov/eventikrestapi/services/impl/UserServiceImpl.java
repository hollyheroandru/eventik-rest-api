package com.egorhristoforov.eventikrestapi.services.impl;

import com.egorhristoforov.eventikrestapi.configuration.jwt.JwtTokenUtil;
import com.egorhristoforov.eventikrestapi.dtos.requests.user.*;
import com.egorhristoforov.eventikrestapi.dtos.responses.user.UserEventStatusResponse;
import com.egorhristoforov.eventikrestapi.dtos.responses.event.EventsListResponse;
import com.egorhristoforov.eventikrestapi.dtos.responses.user.UserCredentialsResponse;
import com.egorhristoforov.eventikrestapi.dtos.responses.user.UserProfileResponse;
import com.egorhristoforov.eventikrestapi.exceptions.BadRequestException;
import com.egorhristoforov.eventikrestapi.exceptions.ForbiddenException;
import com.egorhristoforov.eventikrestapi.exceptions.ResourceNotFoundException;
import com.egorhristoforov.eventikrestapi.exceptions.UnauthorizedException;
import com.egorhristoforov.eventikrestapi.models.Booking;
import com.egorhristoforov.eventikrestapi.models.Event;
import com.egorhristoforov.eventikrestapi.models.User;
import com.egorhristoforov.eventikrestapi.models.UserRole;
import com.egorhristoforov.eventikrestapi.repositories.BookingRepository;
import com.egorhristoforov.eventikrestapi.repositories.EventRepository;
import com.egorhristoforov.eventikrestapi.repositories.UserRepository;
import com.egorhristoforov.eventikrestapi.repositories.UserRoleRepository;
import com.egorhristoforov.eventikrestapi.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import java.util.*;
import java.util.stream.Collectors;


@Service
public class UserServiceImpl implements UserService {

    @PersistenceContext
    private EntityManager em;
    @Autowired
    UserRepository userRepository;
    @Autowired
    UserRoleRepository roleRepository;
    @Autowired
    EventRepository eventRepository;
    @Autowired
    BookingRepository bookingRepository;
    @Autowired
    BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    private JwtTokenUtil jwtUtil;

    @Autowired
    private JavaMailSender javaMailSender;

    private void sendEmail(String email, String subject, String text) {

        SimpleMailMessage msg = new SimpleMailMessage();

        msg.setTo(email);
        msg.setSubject(subject);
        msg.setText(text);

        javaMailSender.send(msg);
    }

    private String generateRandomDigitsCode(int length) {
        StringBuilder code = new StringBuilder();
        Random rnd = new Random();
        for (int i = 0; i < length; i++) {
            int number = rnd.nextInt(9);
            code.append(number);
        }

        return code.toString();
    }

    private boolean hasRole(User user, String roleName) {
        Optional<UserRole> role = roleRepository.findByName(roleName);

        return role.isPresent() && user.getRoles().contains(role.get());
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));
    }

    public UserProfileResponse findUserById(Long id)
            throws ResourceNotFoundException, ForbiddenException, UnauthorizedException {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null) {
            throw new UnauthorizedException("Unauthorized request");
        }

        User currentUser = userRepository.findByEmail(authentication.getName())
                .orElseThrow(() -> new UnauthorizedException("Unauthorized request"));

        User user = userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));

        if (!currentUser.getId().equals(id) && !hasRole(currentUser, "ADMIN")) {
            throw new ForbiddenException("Only access to your profile is allowed");
        }

        return new UserProfileResponse(user.getName(), user.getSurname());
    }

    public Long createUser(UserCreateRequest user) throws BadRequestException {
        User createdUser = userRepository.findByEmail(user.getEmail())
                .orElse(new User());

        if (createdUser.getActivated()) {
            throw new BadRequestException("Email already taken");
        }

        createdUser.setEmail(user.getEmail());
        createdUser.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        createdUser.setName(user.getName());
        createdUser.setSurname(user.getSurname());

        String emailActivationCode = generateRandomDigitsCode(5);
//        sendEmail(user.getEmail(), "Регистрация в приложении Ивентик",
//                "Код подтверждения регистрации: " + emailActivationCode);
//
        createdUser.setEmailConfirmationCode(emailActivationCode);

        userRepository.save(createdUser);
        return createdUser.getId();
    }

    public UserCredentialsResponse confirmUser(Long id, UserConfirmRequest request)
            throws ResourceNotFoundException, BadRequestException {
        User existedUser = userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));

        if (existedUser.getActivated()) {
            throw new BadRequestException("User already activated");
        }

        if (!existedUser.getEmailConfirmationCode().equals(request.getCode())) {
            throw new BadRequestException("Wrong confirmation code");
        }

        existedUser.setActivated(true);
        existedUser.setEmailConfirmationCode(null);

        Optional<UserRole> role = roleRepository.findByName("USER");
        role.ifPresent(userRole -> existedUser.setRoles(new HashSet<>(Collections.singletonList(userRole))));

        userRepository.save(existedUser);

        String accessToken = jwtUtil.generateAccessToken(existedUser);
        String refreshToken = jwtUtil.generateRefreshToken(existedUser);

        return new UserCredentialsResponse(accessToken, refreshToken, existedUser.getId());
    }

    public UserProfileResponse updateUserById(Long id, UserUpdateRequest request)
            throws ResourceNotFoundException, UnauthorizedException, ForbiddenException, BadRequestException {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null) {
            throw new UnauthorizedException("Unauthorized request");
        }

        User currentUser = userRepository.findByEmail(authentication.getName())
                .orElseThrow(() -> new UnauthorizedException("Unauthorized request"));

        User existedUser = userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));

        if (!currentUser.getId().equals(id) && !hasRole(currentUser, "ADMIN")) {
            throw new ForbiddenException("Only access to your profile is allowed");
        }

        String oldPassword = request.getOldPassword();
        String newPassword = request.getNewPassword();
        String newName = request.getName();
        String newSurname = request.getSurname();

        if (newPassword != null) {
            if (oldPassword != null) {
                if (!bCryptPasswordEncoder.matches(oldPassword, existedUser.getPassword())) {
                    throw new BadRequestException("Wrong old password");
                } else {
                    existedUser.setPassword(bCryptPasswordEncoder.encode(newPassword));
                }
            } else {
                throw new BadRequestException("Old password cannot be null");
            }
        } else {
            if (oldPassword != null) {
                throw new BadRequestException("New password cannot be null");
            }
        }

        existedUser.setName(newName == null ? existedUser.getName() : newName);
        existedUser.setSurname(newSurname == null ? existedUser.getSurname() : newSurname);

        userRepository.save(existedUser);

        return new UserProfileResponse(existedUser.getName(), existedUser.getSurname());
    }

    public void deleteUserById(Long id)
            throws ResourceNotFoundException, ForbiddenException, UnauthorizedException {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null) {
            throw new UnauthorizedException("Unauthorized request");
        }

        User currentUser = userRepository.findByEmail(authentication.getName())
                .orElseThrow(() -> new UnauthorizedException("Unauthorized request"));

        if (!userRepository.existsById(id)) {
            throw new ResourceNotFoundException("User not found");
        }

        if (!currentUser.getId().equals(id) && !hasRole(currentUser, "ADMIN")) {
            throw new ForbiddenException("Access to user denied");
        }

        userRepository.deleteById(id);
    }

    public void recoverPassword(PasswordRecoveryRequest request) throws ResourceNotFoundException {
        User existedUser = userRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));

        if (!existedUser.getActivated()) {
            throw new ResourceNotFoundException("User not found");
        }

        String verificationCode = generateRandomDigitsCode(5);
        existedUser.setPasswordConfirmationCode(verificationCode);

        userRepository.save(existedUser);

        sendEmail(request.getEmail(), "Восстановление пароля в приложении Ивентик",
                "Код восстановления пароля: " + verificationCode);
    }

    public void verifyPassword(PasswordVerifyRequest request) throws ResourceNotFoundException, BadRequestException {
        User existedUser = userRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));

        if (!existedUser.getActivated()) {
            throw new ResourceNotFoundException("User not found");
        }

        String confirmationCode = existedUser.getPasswordConfirmationCode();

        if (confirmationCode == null) {
            throw new BadRequestException("Already verified or code was not sent");
        }

        if (!confirmationCode.equals(request.getCode())) {
            throw new BadRequestException("Wrong confirmation code");
        }

        existedUser.setPasswordConfirmationCode(null);
        existedUser.setPassword(bCryptPasswordEncoder.encode(request.getPassword()));

        userRepository.save(existedUser);
    }

    @Transactional
    public List<EventsListResponse> getBookedEventsForUser(Long userId)
            throws ResourceNotFoundException, UnauthorizedException, ForbiddenException {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null) {
            throw new UnauthorizedException("Unauthorized request");
        }

        User currentUser = userRepository.findByEmail(authentication.getName())
                .orElseThrow(() -> new UnauthorizedException("Unauthorized request"));

        if (!currentUser.getId().equals(userId) && !hasRole(currentUser, "ADMIN")) {
            throw new ForbiddenException("Access denied");
        }

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));

        return user.getBookings().stream()
                .map(Booking::getEvent)
                .filter(event -> event.getDate().after(new Date(System.currentTimeMillis() - 3600 * 1000)))
                .sorted(Comparator.comparing(Event::getId).reversed())
                .map(event -> new EventsListResponse(event.getId(), event.getLongitude(), event.getLatitude(),
                        event.getApartment(), event.getTitle(), event.getDate(), event.getLastModifiedDate()))
                .collect(Collectors.toList());
    }

    @Transactional
    public List<EventsListResponse> getCreatedEventsForUser(Long userId)
            throws ResourceNotFoundException, UnauthorizedException, ForbiddenException {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null) {
            throw new UnauthorizedException("Unauthorized request");
        }

        User currentUser = userRepository.findByEmail(authentication.getName())
                .orElseThrow(() -> new UnauthorizedException("Unauthorized request"));

        if (!currentUser.getId().equals(userId) && !hasRole(currentUser, "ADMIN")) {
            throw new ForbiddenException("Access denied");
        }

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));

        return user.getCreatedEvents().stream()
                .filter(event -> event.getDate().after(new Date(System.currentTimeMillis() - 3600 * 1000)))
                .sorted(Comparator.comparing(Event::getId).reversed())
                .map(event -> new EventsListResponse(event.getId(), event.getLongitude(), event.getLatitude(),
                        event.getApartment(), event.getTitle(), event.getDate(), event.getLastModifiedDate()))
                .collect(Collectors.toList());
    }

    public UserEventStatusResponse getStatus(Long userId, Long eventId)
            throws UnauthorizedException, ResourceNotFoundException, ForbiddenException {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null) {
            throw new UnauthorizedException("Unauthorized request");
        }

        User currentUser = userRepository.findByEmail(authentication.getName())
                .orElseThrow(() -> new UnauthorizedException("Unauthorized request"));

        if (!currentUser.getId().equals(userId) && !hasRole(currentUser, "ADMIN")) {
            throw new ForbiddenException("Access denied");
        }

        Event event = eventRepository.findById(eventId)
                .orElseThrow(() -> new ResourceNotFoundException("Event not found"));

        boolean booked = bookingRepository.existsByEventAndVisitor(userId, eventId);
        boolean created = !booked && eventRepository.existsByEventAndOwner(eventId, userId);

        return new UserEventStatusResponse(booked, created);
    }
}
