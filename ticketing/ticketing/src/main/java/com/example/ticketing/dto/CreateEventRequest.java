package com.example.ticketing.dto;

import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Data
public class CreateEventRequest {
    private String title;
    private String description;
    private LocalDateTime eventDate;
    private String status;
    private Long organizerId;
    private Long venueId;
    private List<CreateTicketTypeRequest> ticketTypes;
}
