package com.example.ticketing.controller;

import com.example.ticketing.dto.CreateEventRequest;
import com.example.ticketing.dto.EventResponseDTO;
import com.example.ticketing.dto.RevenueDTO;
import com.example.ticketing.service.EventService;
import com.example.ticketing.service.BookingService;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/events")
public class EventController {
    private EventService eventService;
    private BookingService bookingService;

    public EventController(EventService eventService, BookingService bookingService) {
        this.eventService = eventService;
        this.bookingService = bookingService;
    }

    @PostMapping
    public EventResponseDTO createEvent(@RequestBody CreateEventRequest createEventRequest) {
        return eventService.createEvent(createEventRequest);
    }

    @GetMapping
    public List<EventResponseDTO> getUpcomingEvents() {
        return eventService.getUpcomingEvents();
    }

    @GetMapping("/{id}")
    public EventResponseDTO getEventDetails(@PathVariable Long id) {
        return eventService.getEventDetails(id);
    }

    @GetMapping("/{id}/revenue")
    public RevenueDTO getRevenue(@PathVariable Long id) {
        return bookingService.getTotalRevenueByEventId(id);
    }
}