package com.egorhristoforov.eventikrestapi.services;

import com.egorhristoforov.eventikrestapi.dtos.requests.user.UserUpdateRequest;
import com.egorhristoforov.eventikrestapi.dtos.responses.admin.UsersListResponse;
import com.egorhristoforov.eventikrestapi.dtos.responses.user.UserProfileResponse;
import com.egorhristoforov.eventikrestapi.exceptions.ResourceNotFoundException;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface AdminService {
    List<UsersListResponse> getUsersList()
            throws ResourceNotFoundException;
    void deleteUserById(Long id)
        throws ResourceNotFoundException;

    UserProfileResponse updateUserProfileById(Long id, UserUpdateRequest request)
        throws ResourceNotFoundException;
}
