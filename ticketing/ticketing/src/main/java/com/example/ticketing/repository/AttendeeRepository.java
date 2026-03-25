package com.example.ticketing.repository;

import com.example.ticketing.entity.Attendee;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AttendeeRepository extends JpaRepository<Attendee, Long> {
    boolean existsByEmail(String email);
}