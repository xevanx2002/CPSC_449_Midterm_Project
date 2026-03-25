package com.example.ticketing.dto;

import lombok.Data;
@Data
public class CreateVenueRequest {
    private String name;
    private String address;
    private String city;
    private Integer totalCapacity;
}