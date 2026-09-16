package com.example.eventmanagement.repository;

import com.example.eventmanagement.Registration;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface RegistrationRepository extends JpaRepository<Registration, Long> {

    Optional<Registration> findByUserIdAndEventId(Long userId, Long eventId);

    List<Registration> findByUserId(Long userId);
    void deleteByUserIdAndEventId(Long userId, Long eventId);
    long countByEventId(Long eventId);
}