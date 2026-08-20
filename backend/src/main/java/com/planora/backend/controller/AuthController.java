package com.planora.backend.controller;

import com.planora.backend.dto.RegisterRequest;
import com.planora.backend.service.RegistrationService;

import jakarta.validation.Valid;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AuthController {

    private final RegistrationService registrationService;

    public AuthController(RegistrationService registrationService) {
        this.registrationService = registrationService;
    }

    @PostMapping("/api/v1/auth/register")
    public String register(@Valid @RequestBody RegisterRequest request) {

        registrationService.register(request);

        return "User registered successfully";
    }
}