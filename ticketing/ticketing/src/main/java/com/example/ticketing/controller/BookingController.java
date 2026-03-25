package com.example.ticketing.controller;

import com.example.ticketing.dto.BookingResponseDTO;
import com.example.ticketing.dto.CreateBookingRequest;
import com.example.ticketing.service.BookingService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/bookings")
public class BookingController {
    private BookingService bookingService;

    public BookingController(BookingService bookingService) {
        this.bookingService = bookingService;
    }

    @PostMapping
    public BookingResponseDTO createBooking(@RequestBody CreateBookingRequest createBookingRequest) {
        return bookingService.createBooking(createBookingRequest);
    }

    @PutMapping("/{id}/cancel")
    public BookingResponseDTO cancelBooking(@PathVariable Long id) {
        return bookingService.cancelBooking(id);
    }
}