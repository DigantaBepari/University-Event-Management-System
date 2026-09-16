package com.example.eventmanagement.controller;

import com.example.eventmanagement.Registration;
import com.example.eventmanagement.User;
import com.example.eventmanagement.repository.UserRepository;
import com.example.eventmanagement.service.RegistrationService;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import jakarta.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/student")
public class StudentController {

    private final RegistrationService registrationService;
    private final UserRepository userRepository;

    public StudentController(RegistrationService registrationService,
                             UserRepository userRepository) {
        this.registrationService = registrationService;
        this.userRepository = userRepository;
    }

    @PostMapping("/registrations")
    public Registration registerForEvent(
            @Valid @RequestBody Registration registration,
            Authentication authentication) {

        User user = userRepository.findByEmail(authentication.getName())
                .orElseThrow(() -> new RuntimeException("User not found"));

        registration.setUserId(user.getId());
        registration.setStatus("PENDING");

        return registrationService.saveRegistration(registration);
    }

    @GetMapping("/registrations")
    public List<Registration> getMyRegistrations(Authentication authentication) {

        User user = userRepository.findByEmail(authentication.getName())
                .orElseThrow(() -> new RuntimeException("User not found"));

        return registrationService.getRegistrationsByUser(user.getId());
    }
    @DeleteMapping("/registrations/{eventId}")
    public String cancelRegistration(
            @PathVariable Long eventId,
            Authentication authentication) {

        User user = userRepository.findByEmail(authentication.getName())
                .orElseThrow(() -> new RuntimeException("User not found"));

        registrationService.cancelRegistration(user.getId(), eventId);

        return "Registration cancelled successfully";
    }
}