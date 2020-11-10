package com.egorhristoforov.eventikrestapi.services;

import com.egorhristoforov.eventikrestapi.dtos.requests.admin.AdminUserUpdateRequest;
import com.egorhristoforov.eventikrestapi.dtos.requests.event.EventCreateRequest;
import com.egorhristoforov.eventikrestapi.dtos.responses.admin.UsersListResponse;
import com.egorhristoforov.eventikrestapi.dtos.responses.event.EventCreateResponse;
import com.egorhristoforov.eventikrestapi.dtos.responses.user.UserProfileResponse;
import com.egorhristoforov.eventikrestapi.exceptions.ResourceNotFoundException;
import com.egorhristoforov.eventikrestapi.exceptions.UnauthorizedException;


import java.util.List;

public interface AdminService {
    List<UsersListResponse> getUsersList()
            throws ResourceNotFoundException;
    void deleteUserById(Long id)
        throws ResourceNotFoundException;

    UserProfileResponse updateUserProfileById(Long id, AdminUserUpdateRequest request)
        throws ResourceNotFoundException;
//
//    EventCreateResponse createEvent(EventCreateRequest request)
//            throws ResourceNotFoundException;
}
