package com.egorhristoforov.eventikrestapi.services;

import com.egorhristoforov.eventikrestapi.dtos.requests.auth.AuthLoginRequest;
import com.egorhristoforov.eventikrestapi.dtos.requests.auth.AuthRefreshRequest;
import com.egorhristoforov.eventikrestapi.dtos.responses.user.UserCredentialsResponse;
import com.egorhristoforov.eventikrestapi.exceptions.ForbiddenException;
import com.egorhristoforov.eventikrestapi.exceptions.ResourceNotFoundException;
import com.egorhristoforov.eventikrestapi.exceptions.UnauthorizedException;

public interface AuthService {
    UserCredentialsResponse loginUser(AuthLoginRequest request) throws ResourceNotFoundException, UnauthorizedException;
    UserCredentialsResponse loginAdmin(AuthLoginRequest request) throws ResourceNotFoundException, UnauthorizedException, ForbiddenException;
    UserCredentialsResponse refreshUser(AuthRefreshRequest request) throws UnauthorizedException;
}
