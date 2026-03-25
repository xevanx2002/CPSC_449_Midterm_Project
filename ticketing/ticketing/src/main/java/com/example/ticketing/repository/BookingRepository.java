package com.example.ticketing.repository;

import com.example.ticketing.entity.Booking;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.math.BigDecimal;
import java.util.List;

public interface BookingRepository extends JpaRepository<Booking, Long> {
    boolean existsByAttendee_AttendeeIdAndTicketType_TicketTypeId(Long attendeeId, Long ticketTypeId);
    List<Booking> findByAttendee_AttendeeId(Long attendeeId);

    @Query("""
        SELECT COALESCE(SUM(tt.price), 0)
            FROM Booking b
            JOIN b.ticketType tt
            JOIN tt.event e
            WHERE e.eventId = :eventId
                AND b.paymentStatus = com.example.ticketing.enums.PaymentStatus.CONFIRMED
    """)
    BigDecimal getTotalRevenueByEventId(@Param("eventId") Long eventId);
}