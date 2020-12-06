package com.egorhristoforov.eventikrestapi.controllers.app;

import com.egorhristoforov.eventikrestapi.dtos.requests.admin.*;
import com.egorhristoforov.eventikrestapi.dtos.requests.auth.AuthLoginRequest;
import com.egorhristoforov.eventikrestapi.dtos.responses.admin.*;
import com.egorhristoforov.eventikrestapi.dtos.responses.event.EventCreateResponse;
import com.egorhristoforov.eventikrestapi.dtos.responses.event.EventRetrieveResponse;
import com.egorhristoforov.eventikrestapi.dtos.responses.event.EventUpdateResponse;
import com.egorhristoforov.eventikrestapi.dtos.responses.event.EventsListResponse;
import com.egorhristoforov.eventikrestapi.dtos.responses.user.UserCredentialsResponse;
import com.egorhristoforov.eventikrestapi.exceptions.BadRequestException;
import com.egorhristoforov.eventikrestapi.exceptions.ForbiddenException;
import com.egorhristoforov.eventikrestapi.exceptions.ResourceNotFoundException;
import com.egorhristoforov.eventikrestapi.exceptions.UnauthorizedException;
import com.egorhristoforov.eventikrestapi.services.AdminService;
import com.egorhristoforov.eventikrestapi.services.AuthService;
import com.egorhristoforov.eventikrestapi.services.EventService;
import com.egorhristoforov.eventikrestapi.services.LocationService;
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
    LocationService locationService;

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

    @PostMapping(value = "/users", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Creating new user", authorizations = { @Authorization(value = "Access token") })
    public ResponseEntity<AdminUserProfileResponse> createUser(@Valid @RequestBody AdminUserCreateRequest user) throws ResourceNotFoundException, BadRequestException {
        return ResponseEntity.status(HttpStatus.CREATED).body(adminService.createUser(user));
    }

    @GetMapping(value = "/users", produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Get users list by email pattern or all users list", authorizations = { @Authorization(value = "Access token")})
    public ResponseEntity<List<UsersListResponse>> getUsersListByEmailPatternOrAll(@RequestParam(value = "emailPattern", required = false) String emailPattern)
            throws ResourceNotFoundException {
        return ResponseEntity.ok(adminService.getUsersListByEmailPatternOrAll(emailPattern));
    }

    @GetMapping(value = "/users/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Get user profile", authorizations = { @Authorization(value = "Access token") })
    public ResponseEntity<AdminUserProfileResponse> getUserById(@PathVariable(value = "id") Long userId) throws ResourceNotFoundException {
        return ResponseEntity.ok(adminService.getUserById(userId));
    }

    @DeleteMapping(value = "/users/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @ApiOperation(value = "Delete users by id", authorizations = { @Authorization(value = "Access token") })
    public void deleteUserById(@PathVariable(value = "id") @Positive Long id) throws ResourceNotFoundException {
        adminService.deleteUserById(id);
    }

    @DeleteMapping(value = "/cities/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @ApiOperation(value = "Delete city by id", authorizations = { @Authorization(value = "Access token")})
    public void deleteCityById(@PathVariable(value = "id") @Positive Long cityId) throws ResourceNotFoundException{
        adminService.deleteCityById(cityId);
    }

    @PutMapping(value = "/users/{id}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Update users profiles", authorizations = { @Authorization(value = "Access token") })
    public ResponseEntity<AdminUserProfileResponse> updateUserById(@PathVariable(value = "id") Long userId,
                                                                   @Valid @RequestBody AdminUserUpdateRequest body)
            throws ResourceNotFoundException {
        return ResponseEntity.ok(adminService.updateUserProfileById(userId, body));
    }

    @PutMapping(value = "/cities/{id}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Update city by id", authorizations = { @Authorization(value = "Access token")})
    public ResponseEntity<AdminCityResponse> updateCityById(@PathVariable(value = "id") @Positive Long cityId,
                                            @Valid @RequestBody AdminCityUpdateRequest request)
            throws ResourceNotFoundException {
        return ResponseEntity.ok(adminService.updateCityById(cityId, request));
    }

    @PostMapping(value = "/cities", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Create city", authorizations = { @Authorization(value = "Access token")})
    public ResponseEntity<AdminCityResponse> createCity(@Valid @RequestBody AdminCityCreateRequest body)
            throws BadRequestException, ResourceNotFoundException{
        return ResponseEntity.status(HttpStatus.CREATED).body(adminService.createCity(body));
    }

    @GetMapping(value = "/cities/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Get city by id", authorizations = { @Authorization(value = "Access token")})
    public ResponseEntity<AdminCityResponse> getCityById(@PathVariable(value = "id") Long cityId)
        throws ResourceNotFoundException {
        return ResponseEntity.ok(adminService.getCityById(cityId));
    }

    @PostMapping(value = "/events", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Create event", authorizations = { @Authorization(value = "Access token") })
    public ResponseEntity<AdminEventResponse> createEvent(@Valid @RequestBody AdminEventCreateRequest body)
            throws ResourceNotFoundException {
        return ResponseEntity.status(HttpStatus.CREATED).body(adminService.createEvent(body));
    }

    @GetMapping(value = "/events", produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Get events list", authorizations = { @Authorization(value = "Access token")})
    public ResponseEntity<List<EventsListResponse>> getEvents(@RequestParam(value = "city-id", required = false) @Positive Long cityId)
            throws ResourceNotFoundException{
        return ResponseEntity.ok(adminService.getEventsList(cityId));
    }

    @GetMapping(value = "/events/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Get event", authorizations = { @Authorization(value = "Access token")})
    public ResponseEntity<AdminEventResponse> getEvent(@PathVariable(value = "id") @Positive Long eventId)
            throws ResourceNotFoundException {
        return ResponseEntity.ok(adminService.getEventById(eventId));
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
    public ResponseEntity<AdminEventResponse> updateEvent(@PathVariable(value = "id") @Positive Long eventId,
                                                           @Valid @RequestBody AdminEventUpdateRequest body)
            throws ResourceNotFoundException {
        return ResponseEntity.ok(adminService.updateEvent(eventId, body));
    }

    @GetMapping(value = "/roles", produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Get list of existed roles", authorizations = { @Authorization(value = "Access token") })
    public ResponseEntity<List<UserRolesResponse>> getRoles() throws ResourceNotFoundException {
        return ResponseEntity.ok(adminService.getRoles());
    }

    @GetMapping(value = "/countries", produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Get list of countries", authorizations = { @Authorization(value = "Access token")})
    public ResponseEntity<List<AdminCountriesListResponse>> getCountriesList() {
        return  ResponseEntity.ok(adminService.getCountriesList());
    }

    @DeleteMapping(value = "/countries/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @ApiOperation(value = "Delete country by id", authorizations = { @Authorization(value = "Access token")})
    public void deleteCountryById(@PathVariable(value = "id") Long countryId) throws ResourceNotFoundException {
        adminService.deleteCountryById(countryId);
    }

    @PostMapping(value = "/countries", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Create new country", authorizations = { @Authorization(value = "Access token")})
    public ResponseEntity<AdminCountriesListResponse> createCountry(@Validated @RequestBody AdminCountryCreateRequest request) throws BadRequestException {
        return ResponseEntity.status(HttpStatus.CREATED).body(adminService.createCountry(request));
    }

    @PutMapping(value = "/countries/{id}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Update country", authorizations = { @Authorization(value = "Access token") })
    public ResponseEntity<AdminCountriesListResponse> updateCountry(@PathVariable(value = "id") @Positive Long countryId,
                                                           @Valid @RequestBody AdminCountryUpdateRequest body)
            throws ResourceNotFoundException {
        return ResponseEntity.ok(adminService.updateCountry(countryId, body));
    }

    @GetMapping(value = "/countries/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Get country", authorizations = {@Authorization(value = "Access token")})
    public ResponseEntity<AdminCountriesListResponse> getCountryById(@PathVariable(value = "id") Long countryId) throws ResourceNotFoundException {
        return ResponseEntity.ok(adminService.getCountryById(countryId));
    }

    @GetMapping(value = "/countries/{id}/cities", produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Get cities list for country", authorizations = {@Authorization(value = "Access token")})
    public ResponseEntity<List<AdminCitiesListResponse>> getCitiesListForCountry(@PathVariable(value = "id") Long countryId) throws ResourceNotFoundException {
       return ResponseEntity.ok(adminService.getCitiesListForCountryByCountryId(countryId));
    }
}
