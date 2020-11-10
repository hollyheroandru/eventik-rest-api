package com.egorhristoforov.eventikrestapi.controllers.app;

import com.egorhristoforov.eventikrestapi.dtos.requests.auth.AuthLoginRequest;
import com.egorhristoforov.eventikrestapi.dtos.responses.user.UserCredentialsResponse;
import com.egorhristoforov.eventikrestapi.exceptions.ForbiddenException;
import com.egorhristoforov.eventikrestapi.exceptions.ResourceNotFoundException;
import com.egorhristoforov.eventikrestapi.exceptions.UnauthorizedException;
import com.egorhristoforov.eventikrestapi.services.AuthService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping(value = "/api/v1/admin")
@Api(value = "auth-admin-controller", tags = "/admin")
public class AuthAdminController {

    @Autowired
    AuthService authService;

    @PostMapping(value = "/login", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Login admin")
    public ResponseEntity<UserCredentialsResponse> login(@Valid @RequestBody AuthLoginRequest body)
            throws ResourceNotFoundException, UnauthorizedException, ForbiddenException {
        UserCredentialsResponse response = authService.loginAdmin(body);

        return ResponseEntity.ok(response);
    }
}
