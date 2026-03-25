package com.example.ticketing.controller;

import com.example.ticketing.dto.CreateOrganizerRequest;
import com.example.ticketing.entity.Organizer;
import com.example.ticketing.service.OrganizerService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/organizers")
public class OrganizerController {
    private OrganizerService organizerService;

    public OrganizerController(OrganizerService organizerService) {
        this.organizerService = organizerService;
    }

    @PostMapping
    public Organizer createOrganizer(@RequestBody CreateOrganizerRequest createOrganizerRequest) {
        return organizerService.createOrganizer(createOrganizerRequest);
    }
}