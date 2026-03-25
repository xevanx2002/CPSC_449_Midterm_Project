package com.example.ticketing.dto;

import lombok.Data;

@Data
public class CreateBookingRequest {
    private Long attendeeId;
    private Long ticketTypeId;
}
