package com.example.ticketing.service;

import com.example.ticketing.dto.AttendeeBookingsDTO;
import com.example.ticketing.dto.BookingResponseDTO;
import com.example.ticketing.dto.CreateBookingRequest;
import com.example.ticketing.dto.RevenueDTO;
import com.example.ticketing.entity.Attendee;
import com.example.ticketing.entity.Booking;
import com.example.ticketing.entity.Event;
import com.example.ticketing.entity.TicketType;
import com.example.ticketing.enums.PaymentStatus;
import com.example.ticketing.exception.DuplicateResourceException;
import com.example.ticketing.exception.ResourceNotFoundException;
import com.example.ticketing.exception.InsufficientTicketsException;
import com.example.ticketing.repository.AttendeeRepository;
import com.example.ticketing.repository.BookingRepository;
import com.example.ticketing.repository.EventRepository;
import com.example.ticketing.repository.TicketTypeRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class BookingService {
    private BookingRepository bookingRepository;
    private EventRepository eventRepository;
    private TicketTypeRepository ticketTypeRepository;
    private AttendeeRepository attendeeRepository;

    public BookingService(BookingRepository bookingRepository, AttendeeRepository attendeeRepository,
                          TicketTypeRepository ticketTypeRepository, EventRepository eventRepository) {
        this.bookingRepository = bookingRepository;
        this.eventRepository = eventRepository;
        this.ticketTypeRepository = ticketTypeRepository;
        this.attendeeRepository = attendeeRepository;
    }

    @Transactional
    public BookingResponseDTO createBooking(CreateBookingRequest createBookingRequest) {
        Attendee attendee = attendeeRepository.findById(createBookingRequest.getAttendeeId())
                .orElseThrow(() -> new ResourceNotFoundException("Attendee not found"));

        TicketType ticketType = ticketTypeRepository.findById(createBookingRequest.getTicketTypeId())
                .orElseThrow(() -> new ResourceNotFoundException("TicketType not found"));

        boolean alreadyExists = bookingRepository.existsByAttendee_AttendeeIdAndTicketType_TicketTypeId(
                createBookingRequest.getAttendeeId(),
                createBookingRequest.getTicketTypeId()
        );

        if(alreadyExists) {
            throw new DuplicateResourceException("Booking already exists");
        }
        if(ticketType.getQuantityAvailable() <= 0) {
            throw new InsufficientTicketsException("Not enough TicketTypes");
        }

        ticketType.setQuantityAvailable(ticketType.getQuantityAvailable() - 1);
        Booking booking = new Booking();
        booking.setBookingDate(LocalDateTime.now());
        booking.setPaymentStatus(PaymentStatus.CONFIRMED);
        booking.setAttendee(attendee);
        booking.setTicketType(ticketType);
        Booking savedBooking = bookingRepository.save(booking);

        int year = LocalDateTime.now().getYear();
        String formattedId = String.format("%05d", savedBooking.getBookingId());
        String reference = "TKT-" + year + "-" + formattedId;
        savedBooking.setBookingReference(reference);
        savedBooking = bookingRepository.save(savedBooking);

        return mapToBookingResponse(savedBooking);
    }

    @Transactional
    public BookingResponseDTO cancelBooking(Long bookingId) {
        Booking booking = bookingRepository.findById(bookingId)
                .orElseThrow(() -> new ResourceNotFoundException("Booking not found"));

        if(booking.getPaymentStatus() == PaymentStatus.CANCELLED) {
            throw new DuplicateResourceException("Booking already cancelled");
        }

        booking.setPaymentStatus(PaymentStatus.CANCELLED);
        TicketType ticketType = booking.getTicketType();
        ticketType.setQuantityAvailable(ticketType.getQuantityAvailable() + 1);
        Booking updatedBooking = bookingRepository.save(booking);
        return mapToBookingResponse(updatedBooking);
    }

    public AttendeeBookingsDTO getBookingsForattendee(Long attendeeId) {
        Attendee attendee = attendeeRepository.findById(attendeeId)
                .orElseThrow(() -> new ResourceNotFoundException("Attendee not found"));

        List<BookingResponseDTO> bookings = bookingRepository.findByAttendee_AttendeeId(attendeeId)
                .stream().map(this::mapToBookingResponse).toList();

        return new AttendeeBookingsDTO(attendee.getName(), bookings);
    }

    public RevenueDTO getTotalRevenueByEventId(Long eventId) {
        Event event = eventRepository.findById(eventId)
                .orElseThrow(() -> new ResourceNotFoundException("Event not found"));

        BigDecimal totalRevenue = bookingRepository.getTotalRevenueByEventId(eventId);
        return new RevenueDTO(event.getTitle(), totalRevenue);
    }

    private BookingResponseDTO mapToBookingResponse(Booking booking) {
        return new BookingResponseDTO(
                booking.getBookingReference(), booking.getBookingDate(),
                booking.getPaymentStatus().name(), booking.getAttendee().getName(),
                booking.getTicketType().getEvent().getTitle(),
                booking.getTicketType().getName(), booking.getTicketType().getPrice()
        );
    }
}