package com.egorhristoforov.eventikrestapi.controllers.app;

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
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.Authorization;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.Positive;
import java.util.List;

@RestController
@RequestMapping("api/v1/users")
@Api(value = "user-controller", tags = "/users")
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Get user profile", authorizations = { @Authorization(value = "Access token") })
    public ResponseEntity<UserProfileResponse> getUserById(@PathVariable(value = "id") Long userId)
            throws ResourceNotFoundException, ForbiddenException, UnauthorizedException {
        UserProfileResponse response = userService.findUserById(userId);

        return ResponseEntity.ok(response);
    }

    @PostMapping(value = "", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    @ApiOperation(value = "Creating new user")
    public Long createUser(@Valid @RequestBody UserCreateRequest user) throws BadRequestException {
        return userService.createUser(user);
    }

    @PostMapping(value = "/{id}/confirm", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Confirm user account")
    public ResponseEntity<UserCredentialsResponse> confirmAccount(@PathVariable(value = "id") Long userId,
                                                                  @Valid @RequestBody UserConfirmRequest body)
            throws ResourceNotFoundException, BadRequestException {
        UserCredentialsResponse credentials = userService.confirmUser(userId, body);

        return ResponseEntity.ok(credentials);
    }

    @PutMapping(value = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Update user profile/password", authorizations = { @Authorization(value = "Access token") })
    public ResponseEntity<UserProfileResponse> updateUserById(@PathVariable(value = "id") Long userId,
                                                              @Valid @RequestBody UserUpdateRequest body)
            throws ResourceNotFoundException, UnauthorizedException, ForbiddenException, BadRequestException {
        UserProfileResponse response = userService.updateUserById(userId, body);

        return ResponseEntity.ok(response);
    }

    @DeleteMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @ApiOperation(value = "Delete user account", authorizations = { @Authorization(value = "Access token") })
    public void deleteUser(@PathVariable(value = "id") Long userId)
            throws ResourceNotFoundException, ForbiddenException, UnauthorizedException {
        userService.deleteUserById(userId);
    }

    @PostMapping(value = "/password-recovery", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @ApiOperation(value = "Recovery users password")
    public void passwordRecovery(@Valid @RequestBody PasswordRecoveryRequest body)
            throws ResourceNotFoundException {
        userService.recoverPassword(body);
    }

    @PostMapping(value = "/password-verify", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @ApiOperation(value = "Verify users new password")
    public void passwordVerify(@Valid @RequestBody PasswordVerifyRequest body)
            throws ResourceNotFoundException, BadRequestException {
        userService.verifyPassword(body);
    }

    @GetMapping(value = "/{id}/booked-events", produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Get users booked events", authorizations = { @Authorization(value = "Access token") })
    public ResponseEntity<List<EventsListResponse>> getBookedEvents(@PathVariable(value = "id") @Positive Long userId)
            throws ResourceNotFoundException, UnauthorizedException, ForbiddenException {
        return ResponseEntity.ok(userService.getBookedEventsForUser(userId));
    }

    @GetMapping(value = "/{id}/created-events", produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Get users created events", authorizations = { @Authorization(value = "Access token") })
    public ResponseEntity<List<EventsListResponse>> getCreatedEvents(@PathVariable(value = "id") @Positive Long userId)
            throws ResourceNotFoundException, UnauthorizedException, ForbiddenException {
        return ResponseEntity.ok(userService.getCreatedEventsForUser(userId));
    }

    @GetMapping(value = "/{user-id}/events/{event-id}/status", produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Get users status of event", authorizations = { @Authorization(value = "Access token") })
    public ResponseEntity<UserEventStatusResponse> getStatus(@PathVariable(value = "user-id") @Positive Long userId,
                                                             @PathVariable(value = "event-id") @Positive Long eventId)
            throws UnauthorizedException, ResourceNotFoundException, ForbiddenException {
        return ResponseEntity.ok(userService.getStatus(userId, eventId));
    }
}
