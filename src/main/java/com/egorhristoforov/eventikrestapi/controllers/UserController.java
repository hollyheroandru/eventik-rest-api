package com.egorhristoforov.eventikrestapi.controllers;

import com.egorhristoforov.eventikrestapi.dtos.requests.user.*;
import com.egorhristoforov.eventikrestapi.dtos.responses.user.UserEventStatusResponse;
import com.egorhristoforov.eventikrestapi.dtos.responses.event.EventsListResponse;
import com.egorhristoforov.eventikrestapi.dtos.responses.user.UserCredentialsResponse;
import com.egorhristoforov.eventikrestapi.dtos.responses.user.UserProfileResponse;
import com.egorhristoforov.eventikrestapi.exceptions.BadRequestException;
import com.egorhristoforov.eventikrestapi.exceptions.ForbiddenException;
import com.egorhristoforov.eventikrestapi.exceptions.ResourceNotFoundException;
import com.egorhristoforov.eventikrestapi.exceptions.UnauthorizedException;
import com.egorhristoforov.eventikrestapi.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.Positive;
import java.util.List;

@RestController
@RequestMapping("api/v1/users")
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping("/{id}")
    public ResponseEntity<UserProfileResponse> getUserById(@PathVariable(value = "id") Long userId)
            throws ResourceNotFoundException, ForbiddenException, UnauthorizedException {
        UserProfileResponse response = userService.findUserById(userId);

        return ResponseEntity.ok(response);
    }

    @PostMapping(value = "")
    @ResponseStatus(HttpStatus.CREATED)
    public Long createUser(@Valid @RequestBody UserCreateRequest user) throws BadRequestException {

        return userService.createUser(user);
    }

    @PostMapping(value = "/{id}/confirm")
    public ResponseEntity<UserCredentialsResponse> confirmAccount(@PathVariable(value = "id") Long userId,
                                                                  @Valid @RequestBody UserConfirmRequest body)
            throws ResourceNotFoundException, BadRequestException {
        UserCredentialsResponse credentials = userService.confirmUser(userId, body);

        return ResponseEntity.ok(credentials);
    }

    @PutMapping(value = "/{id}")
    public ResponseEntity<UserProfileResponse> updateUserById(@PathVariable(value = "id") Long userId,
                                                              @Valid @RequestBody UserUpdateRequest body)
            throws ResourceNotFoundException, UnauthorizedException, ForbiddenException, BadRequestException {
        UserProfileResponse response = userService.updateUserById(userId, body);

        return ResponseEntity.ok(response);
    }

    @DeleteMapping(value = "/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteUser(@PathVariable(value = "id") Long userId)
            throws ResourceNotFoundException, ForbiddenException, UnauthorizedException {
        userService.deleteUserById(userId);
    }

    @PostMapping(value = "/password-recovery")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void passwordRecovery(@Valid @RequestBody PasswordRecoveryRequest body)
            throws ResourceNotFoundException {
        userService.recoverPassword(body);
    }

    @PostMapping(value = "/password-verify")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void passwordVerify(@Valid @RequestBody PasswordVerifyRequest body)
            throws ResourceNotFoundException, BadRequestException {
        userService.verifyPassword(body);
    }

    @GetMapping(value = "/{id}/booked-events")
    public ResponseEntity<List<EventsListResponse>> getBookedEvents(@PathVariable(value = "id") @Positive Long userId)
            throws ResourceNotFoundException, UnauthorizedException, ForbiddenException {
        return ResponseEntity.ok(userService.getBookedEventsForUser(userId));
    }

    @GetMapping(value = "/{id}/created-events")
    public ResponseEntity<List<EventsListResponse>> getCreatedEvents(@PathVariable(value = "id") @Positive Long userId)
            throws ResourceNotFoundException, UnauthorizedException, ForbiddenException {
        return ResponseEntity.ok(userService.getCreatedEventsForUser(userId));
    }

    @GetMapping(value = "/{user-id}/events/{event-id}/status")
    public ResponseEntity<UserEventStatusResponse> getStatus(@PathVariable(value = "user-id") @Positive Long userId,
                                                             @PathVariable(value = "event-id") @Positive Long eventId)
            throws UnauthorizedException, ResourceNotFoundException, ForbiddenException {
        return ResponseEntity.ok(userService.getStatus(userId, eventId));
    }
}
