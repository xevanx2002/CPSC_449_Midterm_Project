package com.example.ticketing.service;

import com.example.ticketing.dto.CreateOrganizerRequest;
import com.example.ticketing.entity.Organizer;
import com.example.ticketing.exception.DuplicateResourceException;
import com.example.ticketing.repository.OrganizerRepository;
import org.springframework.stereotype.Service;

@Service
public class OrganizerService {
    private OrganizerRepository organizerRepository;

    public OrganizerService(OrganizerRepository organizerRepository) {
        this.organizerRepository = organizerRepository;
    }

    public Organizer createOrganizer(CreateOrganizerRequest createOrganizerRequest) {
        if (organizerRepository.existsByEmail(createOrganizerRequest.getEmail())) {
            throw new DuplicateResourceException("Email already exists");
        }
        Organizer organizer = new Organizer();
        organizer.setName(createOrganizerRequest.getName());
        organizer.setEmail(createOrganizerRequest.getEmail());
        organizer.setPhone(createOrganizerRequest.getPhone());
        return organizerRepository.save(organizer);
    }
}