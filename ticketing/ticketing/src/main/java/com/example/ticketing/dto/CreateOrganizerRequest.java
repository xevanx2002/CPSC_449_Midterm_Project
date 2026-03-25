package com.example.ticketing.dto;

import lombok.Data;

@Data
public class CreateOrganizerRequest {
    private String name;
    private String email;
    private String phone;
}
