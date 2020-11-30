package com.egorhristoforov.eventikrestapi.services;

import com.egorhristoforov.eventikrestapi.dtos.requests.admin.*;
import com.egorhristoforov.eventikrestapi.dtos.responses.admin.*;
import com.egorhristoforov.eventikrestapi.dtos.responses.event.EventCreateResponse;
import com.egorhristoforov.eventikrestapi.dtos.responses.event.EventUpdateResponse;
import com.egorhristoforov.eventikrestapi.dtos.responses.event.EventsListResponse;
import com.egorhristoforov.eventikrestapi.exceptions.BadRequestException;
import com.egorhristoforov.eventikrestapi.exceptions.ResourceNotFoundException;


import java.util.List;

public interface AdminService {

    List<UsersListResponse> getUsersList()
            throws ResourceNotFoundException;

    void deleteUserById(Long id)
        throws ResourceNotFoundException;

    AdminCityResponse getCityById(Long cityId)
        throws ResourceNotFoundException;

    AdminCityResponse createCity(AdminCityCreateRequest request)
        throws BadRequestException, ResourceNotFoundException;

    AdminUserProfileResponse getUserById(Long userId)
        throws ResourceNotFoundException;

    AdminUserProfileResponse createUser(AdminUserCreateRequest user)
            throws ResourceNotFoundException, BadRequestException;

    AdminCountriesListResponse createCountry(AdminCountryCreateRequest request)
            throws BadRequestException;

    AdminCountriesListResponse updateCountry(Long countryId, AdminCountryUpdateRequest request)
        throws ResourceNotFoundException;

    AdminCountriesListResponse getCountryById(Long countryId)
        throws ResourceNotFoundException;

    List<EventsListResponse> getEventsList(Long cityId)
        throws ResourceNotFoundException;

    List<AdminCountriesListResponse> getCountriesList();

    List<AdminCitiesListResponse> getCitiesListForCountryByCountryId(Long countryId)
        throws ResourceNotFoundException;

    void deleteCountryById(Long countryId)
        throws ResourceNotFoundException;

    AdminUserProfileResponse updateUserProfileById(Long id, AdminUserUpdateRequest request)
        throws ResourceNotFoundException;

    AdminEventResponse createEvent(AdminEventCreateRequest request)
            throws ResourceNotFoundException;

    void deleteEventById(Long eventId)
        throws ResourceNotFoundException;

    AdminEventResponse getEventById(Long eventId)
        throws ResourceNotFoundException;

    AdminEventResponse updateEvent(Long eventId, AdminEventUpdateRequest request)
            throws ResourceNotFoundException;

    List<UserRolesResponse> getRoles()
        throws ResourceNotFoundException;
}
