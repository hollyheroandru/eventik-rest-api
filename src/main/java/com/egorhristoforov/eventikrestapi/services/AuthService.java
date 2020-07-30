package com.egorhristoforov.eventikrestapi.services;

import com.egorhristoforov.eventikrestapi.dtos.requests.Auth.AuthLoginRequest;
import com.egorhristoforov.eventikrestapi.dtos.requests.Auth.AuthRefreshRequest;
import com.egorhristoforov.eventikrestapi.dtos.responses.user.UserCredentialsResponse;
import com.egorhristoforov.eventikrestapi.exceptions.BadRequestException;
import com.egorhristoforov.eventikrestapi.exceptions.ResourceNotFoundException;
import com.egorhristoforov.eventikrestapi.exceptions.UnauthorizedException;

public interface AuthService {
    UserCredentialsResponse loginUser(AuthLoginRequest request) throws ResourceNotFoundException, UnauthorizedException;
    UserCredentialsResponse refreshUser(AuthRefreshRequest request) throws UnauthorizedException;
}
