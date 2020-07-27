package com.egorhristoforov.eventikrestapi.services.Impl;

import com.egorhristoforov.eventikrestapi.configuration.JWT.JwtTokenUtil;
import com.egorhristoforov.eventikrestapi.dtos.requests.AuthLoginRequest;
import com.egorhristoforov.eventikrestapi.dtos.requests.AuthRefreshRequest;
import com.egorhristoforov.eventikrestapi.dtos.responses.UserCredentialsResponse;
import com.egorhristoforov.eventikrestapi.exceptions.BadRequestException;
import com.egorhristoforov.eventikrestapi.exceptions.ResourceNotFoundException;
import com.egorhristoforov.eventikrestapi.exceptions.UnauthorizedException;
import com.egorhristoforov.eventikrestapi.models.User;
import com.egorhristoforov.eventikrestapi.repositories.UserRepository;
import com.egorhristoforov.eventikrestapi.services.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AuthServiceImpl implements AuthService {

    @Autowired
    UserRepository userRepository;

    @Autowired
    BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    private JwtTokenUtil jwtUtil;

    public UserCredentialsResponse loginUser(AuthLoginRequest request) throws ResourceNotFoundException, BadRequestException {
        User user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));

        if (!user.getActivated()) {
            throw new ResourceNotFoundException("User not found");
        }

        if (!bCryptPasswordEncoder.matches(request.getPassword(), user.getPassword())) {
            throw new BadRequestException("Wrong password");
        }

        String accessToken = jwtUtil.generateAccessToken(user);
        String refreshToken = jwtUtil.generateRefreshToken(user);

        return new UserCredentialsResponse(accessToken, refreshToken, user.getId());
    }

    public UserCredentialsResponse refreshUser(AuthRefreshRequest request) throws UnauthorizedException {
        Long userId;
        try {
            userId = jwtUtil.getUserIdFromToken(request.getRefreshToken());
        } catch (Exception e) {
            throw new UnauthorizedException("Invalid token. " + e.getLocalizedMessage());
        }
        if (userId == null) {
            throw new UnauthorizedException("Invalid token");
        }

        try {
            if (jwtUtil.isTokenExpired(request.getRefreshToken())) {
                throw new UnauthorizedException("Token expired");
            }
        } catch (Exception e) {
            throw new UnauthorizedException("Invalid token. " + e.getLocalizedMessage());
        }

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new UnauthorizedException("Invalid token"));

        String accessToken = jwtUtil.generateAccessToken(user);
        String refreshToken = jwtUtil.generateRefreshToken(user);

        return new UserCredentialsResponse(accessToken, refreshToken, user.getId());
    }
}
