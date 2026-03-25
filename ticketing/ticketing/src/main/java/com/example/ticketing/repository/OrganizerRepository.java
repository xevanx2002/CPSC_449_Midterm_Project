package com.example.ticketing.repository;

import com.example.ticketing.entity.Organizer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrganizerRepository extends JpaRepository<Organizer, Long> {
    boolean existsByEmail(String email);
}