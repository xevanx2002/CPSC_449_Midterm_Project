package com.example.ticketing.dto;

import lombok.Data;
import java.math.BigDecimal;

@Data
public class CreateTicketTypeRequest {
    private String name;
    private BigDecimal price;
    private Integer quantityAvailable;
}
