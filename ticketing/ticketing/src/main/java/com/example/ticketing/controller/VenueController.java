package com.example.ticketing.controller;

import com.example.ticketing.dto.CreateVenueRequest;
import com.example.ticketing.entity.Venue;
import com.example.ticketing.service.VenueService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/venues")
public class VenueController {
    private VenueService venueService;

    public VenueController(VenueService venueService) {
        this.venueService = venueService;
    }

    @PostMapping
    public Venue createVenue(@RequestBody CreateVenueRequest createVenueRequest) {
        return venueService.createVenue(createVenueRequest);
    }
}