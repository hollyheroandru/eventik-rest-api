package com.egorhristoforov.eventikrestapi.services;

import com.egorhristoforov.eventikrestapi.dtos.requests.AuthLoginRequest;
import com.egorhristoforov.eventikrestapi.dtos.requests.AuthRefreshRequest;
import com.egorhristoforov.eventikrestapi.dtos.responses.UserCredentialsResponse;
import com.egorhristoforov.eventikrestapi.exceptions.BadRequestException;
import com.egorhristoforov.eventikrestapi.exceptions.ResourceNotFoundException;
import com.egorhristoforov.eventikrestapi.exceptions.UnauthorizedException;

public interface AuthService {
    UserCredentialsResponse loginUser(AuthLoginRequest request) throws ResourceNotFoundException, BadRequestException;
    UserCredentialsResponse refreshUser(AuthRefreshRequest request) throws UnauthorizedException;
}
