package com.example.ticketing.service;

import com.example.ticketing.dto.CreateAttendeeRequest;
import com.example.ticketing.entity.Attendee;
import com.example.ticketing.exception.DuplicateResourceException;
import com.example.ticketing.exception.ResourceNotFoundException;
import com.example.ticketing.repository.AttendeeRepository;
import org.springframework.stereotype.Service;

@Service
public class AttendeeService {
    private AttendeeRepository attendeeRepository;

    public AttendeeService(AttendeeRepository attendeeRepository) {
        this.attendeeRepository = attendeeRepository;
    }

    public Attendee createAttendee(CreateAttendeeRequest createAttendeeRequest) {
        if (attendeeRepository.existsByEmail(createAttendeeRequest.getEmail())) {
            throw new DuplicateResourceException("Email already exists");
        }
        Attendee attendee = new Attendee();
        attendee.setName(createAttendeeRequest.getName());
        attendee.setEmail(createAttendeeRequest.getEmail());
        return attendeeRepository.save(attendee);
    }

    public Attendee getAttendeeById(Long attendeeId) {
        return attendeeRepository.findById(attendeeId)
                .orElseThrow(() -> new ResourceNotFoundException("Attendee not found."));
    }
}