package com.egorhristoforov.eventikrestapi.services.impl;

import com.egorhristoforov.eventikrestapi.dtos.requests.admin.AdminUserUpdateRequest;
import com.egorhristoforov.eventikrestapi.dtos.responses.admin.UsersListResponse;
import com.egorhristoforov.eventikrestapi.dtos.responses.user.UserProfileResponse;
import com.egorhristoforov.eventikrestapi.exceptions.ResourceNotFoundException;
import com.egorhristoforov.eventikrestapi.models.User;
import com.egorhristoforov.eventikrestapi.repositories.CityRepository;
import com.egorhristoforov.eventikrestapi.repositories.EventRepository;
import com.egorhristoforov.eventikrestapi.repositories.UserRepository;
import com.egorhristoforov.eventikrestapi.services.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Comparator;
import java.util.List;
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

    @Override
    public UserProfileResponse updateUserProfileById(Long id, AdminUserUpdateRequest request)
            throws ResourceNotFoundException {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));

        if(request.getNewPassword() != null) {
            user.setPassword(bCryptPasswordEncoder.encode(request.getNewPassword()));
        }
        user.setName(request.getName() == null ? user.getName() : request.getName());
        user.setSurname(request.getSurname() == null ? user.getSurname() : request.getSurname());
        user.setEmail(request.getEmail() == null ? user.getEmail() : request.getEmail());

        userRepository.save(user);
        return new UserProfileResponse(user.getName(), user.getSurname());
    }
}
