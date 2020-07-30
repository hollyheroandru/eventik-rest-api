package com.egorhristoforov.eventikrestapi.services;

import com.egorhristoforov.eventikrestapi.dtos.requests.*;
import com.egorhristoforov.eventikrestapi.dtos.responses.EventStatusResponse;
import com.egorhristoforov.eventikrestapi.dtos.responses.EventsListResponse;
import com.egorhristoforov.eventikrestapi.dtos.responses.UserCredentialsResponse;
import com.egorhristoforov.eventikrestapi.dtos.responses.UserProfileResponse;
import com.egorhristoforov.eventikrestapi.exceptions.BadRequestException;
import com.egorhristoforov.eventikrestapi.exceptions.ForbiddenException;
import com.egorhristoforov.eventikrestapi.exceptions.ResourceNotFoundException;
import com.egorhristoforov.eventikrestapi.exceptions.UnauthorizedException;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.List;

public interface UserService extends UserDetailsService {
    UserProfileResponse findUserById(Long id)
            throws ResourceNotFoundException, ForbiddenException, UnauthorizedException;

    Long createUser(UserCreateRequest user)
            throws BadRequestException;

    UserCredentialsResponse confirmUser(Long id, UserConfirmRequest request)
            throws ResourceNotFoundException, BadRequestException;

    UserProfileResponse updateUserById(Long id, UserUpdateRequest request)
            throws ResourceNotFoundException, UnauthorizedException, ForbiddenException, BadRequestException;

    void deleteUserById(Long id)
            throws ResourceNotFoundException, ForbiddenException, UnauthorizedException;

    void recoverPassword(PasswordRecoveryRequest request)
            throws ResourceNotFoundException;

    void verifyPassword(PasswordVerifyRequest request)
            throws ResourceNotFoundException, BadRequestException;

    List<EventsListResponse> getBookedEventsForUser(Long userId)
            throws ResourceNotFoundException, UnauthorizedException, ForbiddenException;

    List<EventsListResponse> getCreatedEventsForUser(Long userId)
            throws ResourceNotFoundException, UnauthorizedException, ForbiddenException;

    EventStatusResponse getStatus(Long userId, Long eventId)
            throws UnauthorizedException, ResourceNotFoundException, ForbiddenException;
}
