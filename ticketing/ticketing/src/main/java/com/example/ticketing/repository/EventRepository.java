package com.example.ticketing.repository;

import com.example.ticketing.entity.Event;
import com.example.ticketing.enums.EventStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface EventRepository extends JpaRepository<Event, Long> {
    List<Event> findByStatus(EventStatus status);
}