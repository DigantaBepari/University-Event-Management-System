package com.example.eventmanagement.service;

import com.example.eventmanagement.Event;
import com.example.eventmanagement.Registration;
import com.example.eventmanagement.repository.EventRepository;
import com.example.eventmanagement.repository.RegistrationRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RegistrationService {

    private final RegistrationRepository registrationRepository;
    private final EventRepository eventRepository;

    public RegistrationService(RegistrationRepository registrationRepository,
                               EventRepository eventRepository) {
        this.registrationRepository = registrationRepository;
        this.eventRepository = eventRepository;
    }

    public List<Registration> getAllRegistrations() {
        return registrationRepository.findAll();
    }

    public Registration saveRegistration(Registration registration) {

        if (registrationRepository
                .findByUserIdAndEventId(
                        registration.getUserId(),
                        registration.getEventId()
                ).isPresent()) {

            throw new RuntimeException("Already registered for this event");
        }

        return registrationRepository.save(registration);
    }

    public Registration updateStatus(Long id, String status) {

        Registration registration = registrationRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Registration not found"));

        registration.setStatus(status);

        return registrationRepository.save(registration);
    }

    public List<Registration> getRegistrationsByUser(Long userId) {
        return registrationRepository.findByUserId(userId);
    }

    public Registration updateStatusByOrganizer(
            Long id,
            String status,
            Long organizerId) {

        if (!status.equals("PENDING")
                && !status.equals("APPROVED")
                && !status.equals("REJECTED")) {

            throw new RuntimeException("Invalid registration status");
        }

        Registration registration = registrationRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Registration not found"));

        Event event = eventRepository.findById(registration.getEventId())
                .orElseThrow(() -> new RuntimeException("Event not found"));

        if (!event.getOrganizerId().equals(organizerId)) {
            throw new RuntimeException(
                    "You can only manage registrations for your own events"
            );
        }

        registration.setStatus(status);

        return registrationRepository.save(registration);
    }
    public void cancelRegistration(Long userId, Long eventId) {

        Registration registration = registrationRepository
                .findByUserIdAndEventId(userId, eventId)
                .orElseThrow(() -> new RuntimeException("Registration not found"));

        registrationRepository.delete(registration);
    }
    public long getRegistrationCount(Long eventId) {
        return registrationRepository.countByEventId(eventId);
    }
}