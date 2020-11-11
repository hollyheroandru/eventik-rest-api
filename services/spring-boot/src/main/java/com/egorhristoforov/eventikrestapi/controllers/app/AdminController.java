package com.egorhristoforov.eventikrestapi.controllers.app;

import com.egorhristoforov.eventikrestapi.dtos.requests.admin.AdminEventCreateRequest;
import com.egorhristoforov.eventikrestapi.dtos.requests.admin.AdminEventUpdateRequest;
import com.egorhristoforov.eventikrestapi.dtos.requests.admin.AdminUserUpdateRequest;
import com.egorhristoforov.eventikrestapi.dtos.requests.auth.AuthLoginRequest;
import com.egorhristoforov.eventikrestapi.dtos.responses.admin.AdminUserProfileResponse;
import com.egorhristoforov.eventikrestapi.dtos.responses.admin.UsersListResponse;
import com.egorhristoforov.eventikrestapi.dtos.responses.admin.UsersRolesResponse;
import com.egorhristoforov.eventikrestapi.dtos.responses.event.EventCreateResponse;
import com.egorhristoforov.eventikrestapi.dtos.responses.event.EventRetrieveResponse;
import com.egorhristoforov.eventikrestapi.dtos.responses.event.EventUpdateResponse;
import com.egorhristoforov.eventikrestapi.dtos.responses.event.EventsListResponse;
import com.egorhristoforov.eventikrestapi.dtos.responses.user.UserCredentialsResponse;
import com.egorhristoforov.eventikrestapi.exceptions.ForbiddenException;
import com.egorhristoforov.eventikrestapi.exceptions.ResourceNotFoundException;
import com.egorhristoforov.eventikrestapi.exceptions.UnauthorizedException;
import com.egorhristoforov.eventikrestapi.services.AdminService;
import com.egorhristoforov.eventikrestapi.services.AuthService;
import com.egorhristoforov.eventikrestapi.services.EventService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.Authorization;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.Positive;
import java.util.List;

@RestController
@RequestMapping(value = "/api/v1/admin")
@Validated
@Api(value = "admin-controller", tags = "/admin")
public class AdminController {

    @Autowired
    AdminService adminService;

    @Autowired
    AuthService authService;

    @Autowired
    EventService eventService;

    @PostMapping(value = "/login", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Login admin")
    public ResponseEntity<UserCredentialsResponse> login(@Valid @RequestBody AuthLoginRequest body)
            throws ResourceNotFoundException, UnauthorizedException, ForbiddenException {
        UserCredentialsResponse response = authService.loginAdmin(body);

        return ResponseEntity.ok(response);
    }

    @GetMapping(value = "/users", produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Get list of users", authorizations = { @Authorization(value = "Access token") })
    public ResponseEntity<List<UsersListResponse>> getUsers() throws ResourceNotFoundException {
        return ResponseEntity.ok(adminService.getUsersList());
    }

    @GetMapping(value = "/users/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Get user profile", authorizations = { @Authorization(value = "Access token") })
    public ResponseEntity<AdminUserProfileResponse> getUserById(@PathVariable(value = "id") Long userId) throws ResourceNotFoundException {
        return ResponseEntity.ok(adminService.getUserById(userId));
    }

    @DeleteMapping("/users/{id}")
    @ApiOperation(value = "Delete users by id", authorizations = { @Authorization(value = "Access token") })
    public void deleteUserById(@PathVariable(value = "id") Long id) throws ResourceNotFoundException {
        adminService.deleteUserById(id);
    }

    @PutMapping(value = "/users/{id}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Update users profiles", authorizations = { @Authorization(value = "Access token") })
    public ResponseEntity<AdminUserProfileResponse> updateUserById(@PathVariable(value = "id") Long userId,
                                                                   @Valid @RequestBody AdminUserUpdateRequest body)
            throws ResourceNotFoundException {
        return ResponseEntity.ok(adminService.updateUserProfileById(userId, body));
    }

    @PostMapping(value = "/events", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Create event", authorizations = { @Authorization(value = "Access token") })
    public ResponseEntity<EventCreateResponse> createEvent(@Valid @RequestBody AdminEventCreateRequest body)
            throws ResourceNotFoundException {
        return ResponseEntity.status(HttpStatus.CREATED).body(adminService.createEvent(body));
    }

    @GetMapping(value = "/events", produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Get events list", authorizations = { @Authorization(value = "Access token")})
    public ResponseEntity<List<EventsListResponse>> getEvents(@RequestParam(value = "city-id") @Positive Long cityId)
            throws ResourceNotFoundException{
        return ResponseEntity.ok(eventService.getEventsList(cityId));
    }

    @GetMapping(value = "/events/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Retrieve event", authorizations = { @Authorization(value = "Access token")})
    public ResponseEntity<EventRetrieveResponse> getEvent(@PathVariable(value = "id") @Positive Long eventId)
            throws ResourceNotFoundException {
        return ResponseEntity.ok(eventService.getEvent(eventId));
    }

    @DeleteMapping(value = "/events/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @ApiOperation(value = "Delete event", authorizations = { @Authorization(value = "Access token") })
    public void deleteEvent(@PathVariable(value = "id") @Positive Long eventId)
            throws ResourceNotFoundException {
        adminService.deleteEventById(eventId);
    }

    @PutMapping(value = "/events/{id}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Update event", authorizations = { @Authorization(value = "Access token") })
    public ResponseEntity<EventUpdateResponse> updateEvent(@PathVariable(value = "id") @Positive Long eventId,
                                                           @Valid @RequestBody AdminEventUpdateRequest body)
            throws ResourceNotFoundException {
        return ResponseEntity.ok(adminService.updateEvent(eventId, body));
    }

    @GetMapping(value = "/roles")
    @ApiOperation(value = "Get list of existed roles", authorizations = { @Authorization(value = "Access token") })
    public ResponseEntity<List<UsersRolesResponse>> getRoles() throws ResourceNotFoundException {
        return ResponseEntity.ok(adminService.getRoles());
    }
}
