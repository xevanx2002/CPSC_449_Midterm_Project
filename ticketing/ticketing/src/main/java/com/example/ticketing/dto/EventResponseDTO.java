package com.example.ticketing.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.List;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class EventResponseDTO {
    private Long eventId;
    private String title;
    private String description;
    private LocalDateTime eventDate;
    private String status;
    private String organizerName;
    private String venueName;
    private List<TicketTypeDTO> ticketTypes;
}
