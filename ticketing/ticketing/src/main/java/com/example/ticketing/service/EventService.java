package com.example.ticketing.service;

import com.example.ticketing.dto.*;
import com.example.ticketing.entity.*;
import com.example.ticketing.enums.EventStatus;
import com.example.ticketing.exception.ResourceNotFoundException;
import com.example.ticketing.repository.EventRepository;
import com.example.ticketing.repository.OrganizerRepository;
import com.example.ticketing.repository.VenueRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.ArrayList;
import java.util.List;

@Service
public class EventService {
    private EventRepository eventRepository;
    private OrganizerRepository organizerRepository;
    private VenueRepository venueRepository;
    public EventService(EventRepository eventRepository, OrganizerRepository organizerRepository, VenueRepository venueRepository) {
        this.eventRepository = eventRepository;
        this.organizerRepository = organizerRepository;
        this.venueRepository = venueRepository;
    }

    @Transactional
    public EventResponseDTO createEvent(CreateEventRequest createEventRequest) {
        Organizer organizer = organizerRepository.findById(createEventRequest.getOrganizerId())
                .orElseThrow(() -> new ResourceNotFoundException("Organizer"));

        Venue venue = venueRepository.findById(createEventRequest.getVenueId())
                .orElseThrow(() -> new ResourceNotFoundException("Venue"));

        Event event = new Event();
        event.setTitle(createEventRequest.getTitle());
        event.setDescription(createEventRequest.getDescription());
        event.setEventDate(createEventRequest.getEventDate());
        event.setStatus(EventStatus.valueOf(createEventRequest.getStatus()));
        event.setOrganizerId(organizer);
        event.setVenueId(venue);

        List<TicketType> ticketTypes = new ArrayList<>();
        if (createEventRequest.getTicketTypes() != null) {
            for (CreateTicketTypeRequest createTicketTypeRequest : createEventRequest.getTicketTypes()) {
                TicketType ticketType = new TicketType();
                ticketType.setName(createTicketTypeRequest.getName());
                ticketType.setPrice(createTicketTypeRequest.getPrice());
                ticketType.setQuantityAvailable(createTicketTypeRequest.getQuantityAvailable());
                ticketType.setEvent(event);
                ticketTypes.add(ticketType);
            }
        }

        event.setTicketTypes(ticketTypes);
        Event savedEvent = eventRepository.save(event);
        List<TicketTypeDTO> ticketTypeDTOs = savedEvent.getTicketTypes().stream()
                .map(ticket -> new TicketTypeDTO(
                        ticket.getTicketTypeId(), ticket.getName(), ticket.getPrice(),
                        ticket.getQuantityAvailable()
                )).toList();
        return new EventResponseDTO(
                savedEvent.getEventId(), savedEvent.getTitle(), savedEvent.getDescription(),
                savedEvent.getEventDate(), savedEvent.getStatus().name(),
                savedEvent.getOrganizerId().getName(),
                savedEvent.getVenueId().getName(), ticketTypeDTOs
        );
    }

    public Event getEventById(Long eventId) {
        return eventRepository.findById(eventId)
                .orElseThrow(() -> new ResourceNotFoundException("Event"));
    }
}