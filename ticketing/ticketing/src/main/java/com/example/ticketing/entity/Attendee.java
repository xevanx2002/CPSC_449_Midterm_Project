package com.example.ticketing.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Entity
@Table(name = "attendee")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Attendee {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer attendeeId;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String email;
}