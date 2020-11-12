package com.egorhristoforov.eventikrestapi.services;

import com.egorhristoforov.eventikrestapi.dtos.requests.admin.*;
import com.egorhristoforov.eventikrestapi.dtos.responses.admin.AdminCountriesListResponse;
import com.egorhristoforov.eventikrestapi.dtos.responses.admin.AdminUserProfileResponse;
import com.egorhristoforov.eventikrestapi.dtos.responses.admin.UsersListResponse;
import com.egorhristoforov.eventikrestapi.dtos.responses.admin.UserRolesResponse;
import com.egorhristoforov.eventikrestapi.dtos.responses.event.EventCreateResponse;
import com.egorhristoforov.eventikrestapi.dtos.responses.event.EventUpdateResponse;
import com.egorhristoforov.eventikrestapi.dtos.responses.location.CountriesListResponse;
import com.egorhristoforov.eventikrestapi.exceptions.BadRequestException;
import com.egorhristoforov.eventikrestapi.exceptions.ResourceNotFoundException;


import java.util.List;

public interface AdminService {

    List<UsersListResponse> getUsersList()
            throws ResourceNotFoundException;

    void deleteUserById(Long id)
        throws ResourceNotFoundException;

    AdminUserProfileResponse getUserById(Long userId)
        throws ResourceNotFoundException;

    AdminUserProfileResponse createUser(AdminUserCreateRequest user)
            throws ResourceNotFoundException, BadRequestException;

    AdminCountriesListResponse createCountry(AdminCountryCreateRequest request)
            throws BadRequestException;

    List<AdminCountriesListResponse> getCountriesList();

    void deleteCountryById(Long countryId)
        throws ResourceNotFoundException;

    AdminUserProfileResponse updateUserProfileById(Long id, AdminUserUpdateRequest request)
        throws ResourceNotFoundException;

    EventCreateResponse createEvent(AdminEventCreateRequest request)
            throws ResourceNotFoundException;

    void deleteEventById(Long eventId)
        throws ResourceNotFoundException;

    EventUpdateResponse updateEvent(Long eventId, AdminEventUpdateRequest request)
            throws ResourceNotFoundException;

    List<UserRolesResponse> getRoles()
        throws ResourceNotFoundException;
}
