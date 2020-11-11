package com.egorhristoforov.eventikrestapi.services.impl;

import com.egorhristoforov.eventikrestapi.dtos.requests.admin.AdminEventCreateRequest;
import com.egorhristoforov.eventikrestapi.dtos.requests.admin.AdminEventUpdateRequest;
import com.egorhristoforov.eventikrestapi.dtos.requests.admin.AdminUserUpdateRequest;
import com.egorhristoforov.eventikrestapi.dtos.requests.event.EventCreateRequest;
import com.egorhristoforov.eventikrestapi.dtos.requests.event.EventUpdateRequest;
import com.egorhristoforov.eventikrestapi.dtos.responses.admin.AdminUserProfileResponse;
import com.egorhristoforov.eventikrestapi.dtos.responses.admin.UsersListResponse;
import com.egorhristoforov.eventikrestapi.dtos.responses.admin.UsersRolesResponse;
import com.egorhristoforov.eventikrestapi.dtos.responses.event.EventCreateResponse;
import com.egorhristoforov.eventikrestapi.dtos.responses.event.EventUpdateResponse;
import com.egorhristoforov.eventikrestapi.dtos.responses.event.EventsListResponse;
import com.egorhristoforov.eventikrestapi.dtos.responses.user.UserProfileResponse;
import com.egorhristoforov.eventikrestapi.exceptions.ForbiddenException;
import com.egorhristoforov.eventikrestapi.exceptions.ResourceNotFoundException;
import com.egorhristoforov.eventikrestapi.exceptions.UnauthorizedException;
import com.egorhristoforov.eventikrestapi.models.City;
import com.egorhristoforov.eventikrestapi.models.Event;
import com.egorhristoforov.eventikrestapi.models.User;
import com.egorhristoforov.eventikrestapi.models.UserRole;
import com.egorhristoforov.eventikrestapi.repositories.CityRepository;
import com.egorhristoforov.eventikrestapi.repositories.EventRepository;
import com.egorhristoforov.eventikrestapi.repositories.UserRepository;
import com.egorhristoforov.eventikrestapi.repositories.UserRoleRepository;
import com.egorhristoforov.eventikrestapi.services.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class AdminServiceImpl implements AdminService {

    @Autowired
    UserRepository userRepository;

    @Autowired
    BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    EventRepository eventRepository;

    @Autowired
    CityRepository cityRepository;

    @Autowired
    UserRoleRepository userRoleRepository;

    @Transactional
    public List<UsersListResponse> getUsersList() throws ResourceNotFoundException {
        return userRepository.findAll()
                .stream()
                .sorted(Comparator.comparing(User::getId).reversed())
                .map(user -> new UsersListResponse(user.getId(), user.getEmail(), user.getName(), user.getSurname()))
                .collect(Collectors.toList());
    }

    @Override
    public void deleteUserById(Long id) throws ResourceNotFoundException {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));
        userRepository.delete(user);
    }

    private List<String> getRolesNameByUser(User user) {
        return user.getRoles().stream()
                .map(userRoles -> userRoles.getName())
                .collect(Collectors.toList());
    }

    @Override
    public AdminUserProfileResponse updateUserProfileById(Long id, AdminUserUpdateRequest request)
            throws ResourceNotFoundException {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));

        if(request.getPassword() != null) {
            user.setPassword(bCryptPasswordEncoder.encode(request.getPassword()));
        }

        user.setName(request.getName() == null ? user.getName() : request.getName());
        user.setSurname(request.getSurname() == null ? user.getSurname() : request.getSurname());
        user.setEmail(request.getEmail() == null ? user.getEmail() : request.getEmail());

        if(request.getRoleId() != null) {
            user.getRoles().add(userRoleRepository.findById(request.getRoleId())
                    .orElseThrow(() -> new ResourceNotFoundException("Role not found")));
        }

        userRepository.save(user);
        return new AdminUserProfileResponse(user.getName(), user.getSurname(), user.getEmail(), getRolesNameByUser(user));
    }

    @Override
    public EventCreateResponse createEvent(AdminEventCreateRequest request)
            throws ResourceNotFoundException {

        User owner = userRepository.findById(request.getOwnerId())
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));

        City eventCity = cityRepository.findById(request.getCityId())
                .orElseThrow(() -> new ResourceNotFoundException("Provided city not found"));

        Event createdEvent = new Event();
        createdEvent.setCity(eventCity);
        createdEvent.setApartment(request.getApartment());
        createdEvent.setTitle(request.getTitle());
        createdEvent.setDescription(request.getDescription());
        createdEvent.setDate(request.getDate());
        createdEvent.setCreatedDate(new Date());
        //createdEvent.setModifiedDate(new Date());
        createdEvent.setLongitude(request.getLongitude());
        createdEvent.setLatitude(request.getLatitude());
        createdEvent.setRegistrationRequired(request.isRegistrationRequired());
        createdEvent.setOwner(owner);

        eventRepository.save(createdEvent);

        EventCreateResponse response = new EventCreateResponse();
        response.setId(createdEvent.getId());
        response.setLatitude(createdEvent.getLatitude());
        response.setLongitude(createdEvent.getLongitude());
        response.setApartment(createdEvent.getApartment());
        response.setTitle(createdEvent.getTitle());
        response.setDescription(createdEvent.getDescription());
        response.setDate(createdEvent.getDate());
        response.setCountOfVisitors(0);
        response.setRegistrationRequired(createdEvent.isRegistrationRequired());
        response.setLastModifiedDate(createdEvent.getLastModifiedDate());

        return response;
    }

    @Override
    public void deleteEventById(Long eventId) throws ResourceNotFoundException {
        Event event = eventRepository.findById(eventId)
                .orElseThrow(() -> new ResourceNotFoundException("Event not found"));
        eventRepository.delete(event);
    }

    @Override
    public EventUpdateResponse updateEvent(Long eventId, AdminEventUpdateRequest request)
            throws ResourceNotFoundException {

        Event event = eventRepository.findById(eventId)
                .orElseThrow(() -> new ResourceNotFoundException("Event not found"));

        event.setLongitude(request.getLongitude() == null ? event.getLongitude() : request.getLongitude());
        event.setLatitude(request.getLatitude() == null ? event.getLatitude() : request.getLatitude());
        event.setApartment(request.getApartment() == null ? event.getApartment() : request.getApartment());
        event.setTitle(request.getTitle() == null ? event.getTitle() : request.getTitle());
        event.setDescription(request.getDescription() == null ? event.getDescription() : request.getDescription());
        event.setDate(request.getDate() == null ? event.getDate() : request.getDate());
        event.setRegistrationRequired(request.isRegistrationRequired() == null ? event.isRegistrationRequired() : request.isRegistrationRequired());
        event.setOwner(request.getOwnerId() == null ? event.getOwner() :
                userRepository.findById(request.getOwnerId())
                .orElseThrow(() -> new ResourceNotFoundException("User not found")));
        //event.setModifiedDate(new Date());

        eventRepository.save(event);

        EventUpdateResponse response = new EventUpdateResponse();
        response.setId(event.getId());
        response.setLongitude(event.getLongitude());
        response.setLatitude(event.getLatitude());
        response.setApartment(event.getApartment());
        response.setTitle(event.getTitle());
        response.setDescription(event.getDescription());
        response.setDate(event.getDate());
        response.setCountOfVisitors(eventRepository.countOfVisitors(eventId));
        response.setRegistrationRequired(event.isRegistrationRequired());
        response.setLastModifiedDate(event.getLastModifiedDate());

        return response;
    }

    @Transactional
    public List<UsersRolesResponse> getRoles() throws ResourceNotFoundException {
        return userRoleRepository.findAll()
                .stream()
                .sorted(Comparator.comparing(UserRole::getId).reversed())
                .map(role -> new UsersRolesResponse(role.getId(), role.getName()))
                .collect(Collectors.toList());
    }
}
