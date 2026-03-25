package com.example.ticketing.controller;

import com.example.ticketing.dto.CreateAttendeeRequest;
import com.example.ticketing.dto.AttendeeBookingsDTO;
import com.example.ticketing.entity.Attendee;
import com.example.ticketing.service.AttendeeService;
import com.example.ticketing.service.BookingService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/attendees")
public class AttendeeController {
    private AttendeeService attendeeService;
    private BookingService bookingService;

    public AttendeeController(AttendeeService attendeeService, BookingService bookingService) {
        this.attendeeService = attendeeService;
        this.bookingService = bookingService;
    }

    @PostMapping
    public Attendee createAttendee(@RequestBody CreateAttendeeRequest createAttendeeRequest) {
        return attendeeService.createAttendee(createAttendeeRequest);
    }

    @GetMapping("/{id}/bookings")
    public AttendeeBookingsDTO getBookings(@PathVariable long id) {
        return bookingService.getBookingsForattendee(id);
    }
}