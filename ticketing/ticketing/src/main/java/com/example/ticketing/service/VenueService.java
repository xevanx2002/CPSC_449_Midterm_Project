package com.example.ticketing.service;

import com.example.ticketing.dto.CreateVenueRequest;
import com.example.ticketing.entity.Venue;
import com.example.ticketing.repository.VenueRepository;
import org.springframework.stereotype.Service;

@Service
public class VenueService {
    private VenueRepository venueRepository;

    public VenueService(VenueRepository venueRepository) {
        this.venueRepository = venueRepository;
    }

    public Venue createVenue(CreateVenueRequest createVenueRequest) {
        Venue venue = new Venue();
        venue.setName(createVenueRequest.getName());
        venue.setAddress(createVenueRequest.getAddress());
        venue.setCity(createVenueRequest.getCity());
        venue.setTotalCapacity(createVenueRequest.getTotalCapacity());
        return venueRepository.save(venue);
    }
}