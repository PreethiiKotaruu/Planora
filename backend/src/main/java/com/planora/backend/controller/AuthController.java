package com.planora.backend.controller;

import com.planora.backend.dto.LoginRequest;
import com.planora.backend.dto.LoginResponse;
import com.planora.backend.dto.RegisterRequest;

import com.planora.backend.entity.User;

import com.planora.backend.service.AuthenticationService;
import com.planora.backend.service.JwtService;
import com.planora.backend.service.RegistrationService;

import jakarta.validation.Valid;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
@RestController
public class AuthController {

    private final RegistrationService registrationService;
    private final AuthenticationService authenticationService;
    private final JwtService jwtService;

    public AuthController(
            RegistrationService registrationService,
            AuthenticationService authenticationService,
            JwtService jwtService) {

        this.registrationService = registrationService;
        this.authenticationService = authenticationService;
        this.jwtService = jwtService;
    }

    @PostMapping("/api/v1/auth/register")
    public String register(
            @Valid @RequestBody RegisterRequest request) {

        registrationService.register(request);

        return "User registered successfully";
    }

    @PostMapping("/api/v1/auth/login")
    public LoginResponse login(
            @Valid @RequestBody LoginRequest request) {

        User user =
                authenticationService.authenticate(request);

        String token =
                jwtService.generateToken(user);

        return new LoginResponse(token);
    }
    @GetMapping("/api/v1/auth/me")
    public Map<String, String> me() {

        Authentication authentication =
                SecurityContextHolder
                        .getContext()
                        .getAuthentication();

        return Map.of(
                "email",
                authentication.getName()
        );
    }
}