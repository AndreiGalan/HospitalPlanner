package com.example.HospitalPlanner.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalTime;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
@Entity
@Table(name = "doctors")
public class DoctorEntity {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = jakarta.persistence.GenerationType.IDENTITY)
    private Long id;
    @Column(name = "first_name")
    private String firstName;
    @Column(name = "last_name")
    private String lastName;
    @Column(name="specialization")
    private String specialization;
    @Column(name = "program_start")
    private LocalTime programStart;
    @Column(name = "program_end")
    private LocalTime programEnd;
}