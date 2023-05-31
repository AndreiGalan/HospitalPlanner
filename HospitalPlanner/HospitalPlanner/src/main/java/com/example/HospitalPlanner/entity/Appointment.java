package com.example.HospitalPlanner.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


import java.time.LocalDate;
import java.time.LocalTime;

@Entity
@Table(name = "appointments")
@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class Appointment {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    @JoinColumn(name = "doctor_id")
    private DoctorEntity doctorEntity;
    @ManyToOne
    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    @JoinColumn(name = "patient_id")
    private PatientEntity patientEntity;
    @Column(name = "appointment_date")
    private LocalDate appointmentDate ;
    @Column(name = "appointment_time")
    private LocalTime appointmentTime ;
    @Column(name = "appointment_type")
    private String appointmentType ;

    @Override
    public String toString() {
        return "Appointment{" +
                "id=" + id +
                ", doctorEntity=" + doctorEntity +
                ", patientId=" + patientEntity +
                ", appointmentDate=" + appointmentDate +
                ", appointmentTime=" + appointmentTime +
                ", appointmentType='" + appointmentType + '\'' +
                '}' + "\n";
    }
}
