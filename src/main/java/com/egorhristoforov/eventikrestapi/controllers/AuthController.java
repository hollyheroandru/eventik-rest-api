package com.egorhristoforov.eventikrestapi.controllers;

import com.egorhristoforov.eventikrestapi.dtos.requests.AuthLoginRequest;
import com.egorhristoforov.eventikrestapi.dtos.requests.AuthRefreshRequest;
import com.egorhristoforov.eventikrestapi.dtos.responses.UserCredentialsResponse;
import com.egorhristoforov.eventikrestapi.exceptions.BadRequestException;
import com.egorhristoforov.eventikrestapi.exceptions.ResourceNotFoundException;
import com.egorhristoforov.eventikrestapi.exceptions.UnauthorizedException;
import com.egorhristoforov.eventikrestapi.services.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping(value = "/api/v1/auth")
public class AuthController {

    @Autowired
    AuthService authService;

    @PostMapping(value = "/login")
    public ResponseEntity<UserCredentialsResponse> login(@Valid @RequestBody AuthLoginRequest body)
            throws ResourceNotFoundException, BadRequestException {
        UserCredentialsResponse response = authService.loginUser(body);

        return ResponseEntity.ok(response);
    }

    @PostMapping(value = "/refresh")
    public ResponseEntity<UserCredentialsResponse> refresh(@Valid @RequestBody AuthRefreshRequest body)
            throws UnauthorizedException {
        UserCredentialsResponse response = authService.refreshUser(body);

        return ResponseEntity.ok(response);
    }
}
