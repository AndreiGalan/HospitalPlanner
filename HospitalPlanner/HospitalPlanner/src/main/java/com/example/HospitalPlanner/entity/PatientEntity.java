package com.example.HospitalPlanner.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Entity
@Table(name = "patients")
@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class PatientEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;
    @Column(name = "first_name")
    private String firstName;
    @Column(name = "last_name")
    private String lastName;
    @Column(name="date_of_birth")
    private LocalDate dateOfBirth;
    @Column(name = "address")
    private String address ;
    @Column(name = "email")
    private String email ;
    @Column(name = "phone_number")
    private String phoneNumber;
}
