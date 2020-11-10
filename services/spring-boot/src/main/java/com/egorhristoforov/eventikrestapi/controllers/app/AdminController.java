package com.egorhristoforov.eventikrestapi.controllers.app;

import com.egorhristoforov.eventikrestapi.dtos.requests.auth.AuthLoginRequest;
import com.egorhristoforov.eventikrestapi.dtos.requests.user.UserUpdateRequest;
import com.egorhristoforov.eventikrestapi.dtos.responses.admin.UsersListResponse;
import com.egorhristoforov.eventikrestapi.dtos.responses.user.UserCredentialsResponse;
import com.egorhristoforov.eventikrestapi.dtos.responses.user.UserProfileResponse;
import com.egorhristoforov.eventikrestapi.exceptions.BadRequestException;
import com.egorhristoforov.eventikrestapi.exceptions.ForbiddenException;
import com.egorhristoforov.eventikrestapi.exceptions.ResourceNotFoundException;
import com.egorhristoforov.eventikrestapi.exceptions.UnauthorizedException;
import com.egorhristoforov.eventikrestapi.services.AdminService;
import com.egorhristoforov.eventikrestapi.services.AuthService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.Authorization;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
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

    @DeleteMapping("/users/{id}")
    @ApiOperation(value = "Delete users by id", authorizations = { @Authorization(value = "Access token") })
    public void deleteUserById(@PathVariable(value = "id") Long id) throws ResourceNotFoundException {
        adminService.deleteUserById(id);
    }

    @PutMapping(value = "/users/{id}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Update users profiles", authorizations = { @Authorization(value = "Access token") })
    public ResponseEntity<UserProfileResponse> updateUserById(@PathVariable(value = "id") Long userId,
                                                              @Valid @RequestBody UserUpdateRequest body)
            throws ResourceNotFoundException, UnauthorizedException, ForbiddenException, BadRequestException {
        UserProfileResponse response = adminService.updateUserProfileById(userId, body);

        return ResponseEntity.ok(response);
    }

}
