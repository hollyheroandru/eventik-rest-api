package com.egorhristoforov.eventikrestapi.services.impl;

import com.egorhristoforov.eventikrestapi.configuration.jwt.JwtTokenUtil;
import com.egorhristoforov.eventikrestapi.dtos.requests.auth.AuthLoginRequest;
import com.egorhristoforov.eventikrestapi.dtos.requests.auth.AuthRefreshRequest;
import com.egorhristoforov.eventikrestapi.dtos.responses.user.UserCredentialsResponse;
import com.egorhristoforov.eventikrestapi.exceptions.ForbiddenException;
import com.egorhristoforov.eventikrestapi.exceptions.ResourceNotFoundException;
import com.egorhristoforov.eventikrestapi.exceptions.UnauthorizedException;
import com.egorhristoforov.eventikrestapi.models.User;
import com.egorhristoforov.eventikrestapi.models.UserRole;
import com.egorhristoforov.eventikrestapi.repositories.UserRepository;
import com.egorhristoforov.eventikrestapi.repositories.UserRoleRepository;
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

    @Autowired
    UserRoleRepository roleRepository;

    private boolean hasRole(User user, String roleName) {
        Optional<UserRole> role = roleRepository.findByName(roleName);

        return role.isPresent() && user.getRoles().contains(role.get());
    }

    public UserCredentialsResponse loginUser(AuthLoginRequest request) throws ResourceNotFoundException, UnauthorizedException {
        User user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));

        if (!user.isEnabled()) {
            throw new ResourceNotFoundException("User not found");
        }

        if (!bCryptPasswordEncoder.matches(request.getPassword(), user.getPassword())) {
            throw new UnauthorizedException("Wrong credentials");
        }

        String accessToken = jwtUtil.generateAccessToken(user);
        String refreshToken = jwtUtil.generateRefreshToken(user);

        return new UserCredentialsResponse(accessToken, refreshToken, user.getId());
    }

    public UserCredentialsResponse loginAdmin(AuthLoginRequest request) throws ResourceNotFoundException, UnauthorizedException, ForbiddenException {
        User user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));

        if (!user.isActivated()) {
            throw new ResourceNotFoundException("User not found");
        }

        if (!bCryptPasswordEncoder.matches(request.getPassword(), user.getPassword())) {
            throw new UnauthorizedException("Wrong credentials");
        }

        if(!hasRole(user, "ADMIN") && !hasRole(user, "MODERATOR")) {
            throw new ForbiddenException("Not enough rights");
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
