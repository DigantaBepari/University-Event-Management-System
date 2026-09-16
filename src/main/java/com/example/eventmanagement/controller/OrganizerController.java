package com.example.eventmanagement.controller;

import com.example.eventmanagement.Event;
import com.example.eventmanagement.Registration;
import com.example.eventmanagement.User;
import com.example.eventmanagement.repository.UserRepository;
import com.example.eventmanagement.service.EventService;
import com.example.eventmanagement.service.RegistrationService;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import jakarta.validation.Valid;
import java.util.List;
import com.example.eventmanagement.dto.EventResponse;

@RestController
@RequestMapping("/organizer")
public class OrganizerController {

    private final EventService eventService;
    private final RegistrationService registrationService;
    private final UserRepository userRepository;

    public OrganizerController(EventService eventService,
                               RegistrationService registrationService,
                               UserRepository userRepository) {
        this.eventService = eventService;
        this.registrationService = registrationService;
        this.userRepository = userRepository;
    }

    @PostMapping("/events")
    public Event createEvent(@Valid @RequestBody Event event,
                             Authentication authentication) {

        User user = userRepository.findByEmail(authentication.getName())
                .orElseThrow(() -> new RuntimeException("User not found"));

        event.setOrganizerId(user.getId());

        return eventService.saveEvent(event);
    }

    @GetMapping("/registrations")
    public List<Registration> getAllRegistrations() {
        return registrationService.getAllRegistrations();
    }

    @GetMapping("/events")
    public List<EventResponse> getMyEvents(Authentication authentication) {

        User user = userRepository.findByEmail(authentication.getName())
                .orElseThrow(() -> new RuntimeException("User not found"));

        return eventService.getEventsByOrganizer(user.getId())
                .stream()
                .map(event -> new EventResponse(
                        event.getId(),
                        event.getTitle(),
                        event.getDescription(),
                        event.getLocation(),
                        event.getDate(),
                        event.getCategoryId(),
                        event.getOrganizerId(),
                        registrationService.getRegistrationCount(event.getId())
                ))
                .toList();
    }

    @PutMapping("/events/{id}")
    public Event updateEvent(
            @PathVariable Long id,
            @Valid @RequestBody Event event,
            Authentication authentication) {

        User user = userRepository.findByEmail(authentication.getName())
                .orElseThrow(() -> new RuntimeException("User not found"));

        Event existingEvent = eventService.getEventById(id);

        if (!existingEvent.getOrganizerId().equals(user.getId())) {
            throw new RuntimeException("You can only update your own events");
        }

        event.setId(id);
        event.setOrganizerId(user.getId());

        return eventService.saveEvent(event);
    }

    @DeleteMapping("/events/{id}")
    public String deleteEvent(
            @PathVariable Long id,
            Authentication authentication) {

        User user = userRepository.findByEmail(authentication.getName())
                .orElseThrow(() -> new RuntimeException("User not found"));

        Event existingEvent = eventService.getEventById(id);

        if (!existingEvent.getOrganizerId().equals(user.getId())) {
            throw new RuntimeException("You can only delete your own events");
        }

        eventService.deleteEvent(id);

        return "Event deleted successfully";
    }
    @PutMapping("/registrations/{id}")
    public Registration updateRegistrationStatus(
            @PathVariable Long id,
            @RequestParam String status) {

        return registrationService.updateStatus(id, status);
    }
}