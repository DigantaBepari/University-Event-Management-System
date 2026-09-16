package com.example.eventmanagement.controller;

import com.example.eventmanagement.User;
import com.example.eventmanagement.dto.LoginRequest;
import com.example.eventmanagement.dto.LoginResponse;
import com.example.eventmanagement.dto.RegisterRequest;
import com.example.eventmanagement.service.AuthService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/register")
    public User register(@Valid @RequestBody RegisterRequest request) {
        return authService.registerStudent(request);
    }

    @PostMapping("/login")
    public LoginResponse login(@Valid @RequestBody LoginRequest request) {
        return authService.login(request);
    }
}