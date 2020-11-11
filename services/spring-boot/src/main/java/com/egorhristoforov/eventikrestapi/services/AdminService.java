package com.egorhristoforov.eventikrestapi.services;

import com.egorhristoforov.eventikrestapi.dtos.requests.admin.AdminEventCreateRequest;
import com.egorhristoforov.eventikrestapi.dtos.requests.admin.AdminEventUpdateRequest;
import com.egorhristoforov.eventikrestapi.dtos.requests.admin.AdminUserUpdateRequest;
import com.egorhristoforov.eventikrestapi.dtos.requests.event.EventCreateRequest;
import com.egorhristoforov.eventikrestapi.dtos.requests.event.EventUpdateRequest;
import com.egorhristoforov.eventikrestapi.dtos.responses.admin.UsersListResponse;
import com.egorhristoforov.eventikrestapi.dtos.responses.admin.UsersRolesResponse;
import com.egorhristoforov.eventikrestapi.dtos.responses.event.EventCreateResponse;
import com.egorhristoforov.eventikrestapi.dtos.responses.event.EventUpdateResponse;
import com.egorhristoforov.eventikrestapi.dtos.responses.event.EventsListResponse;
import com.egorhristoforov.eventikrestapi.dtos.responses.user.UserProfileResponse;
import com.egorhristoforov.eventikrestapi.exceptions.ForbiddenException;
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

    EventCreateResponse createEvent(AdminEventCreateRequest request)
            throws ResourceNotFoundException;

    void deleteEventById(Long eventId)
        throws ResourceNotFoundException;

    EventUpdateResponse updateEvent(Long eventId, AdminEventUpdateRequest request)
            throws ResourceNotFoundException;

    List<UsersRolesResponse> getRoles()
        throws ResourceNotFoundException;
}
