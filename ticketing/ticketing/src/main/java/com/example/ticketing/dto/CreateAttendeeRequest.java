package com.example.ticketing.dto;

import lombok.Data;

@Data
public class CreateAttendeeRequest {
    private String name;
    private String email;
}
