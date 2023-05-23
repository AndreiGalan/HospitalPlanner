package com.example.testui.model;

import java.time.LocalDate;
import java.time.LocalTime;

public class Appointment {

    private Long id;
    private DoctorEntity doctorEntity;
    private PatientEntity patientEntity;
    private LocalDate appointmentDate;
    private LocalTime appointmentTime;
    private String appointmentType;


    @Override
    public String toString() {
        return "Appointment{" +
                "id=" + id +
                ", doctorEntity=" + doctorEntity +
                ", patientEntity=" + patientEntity +
                ", appointmentDate=" + appointmentDate +
                ", appointmentTime=" + appointmentTime +
                ", appointmentType='" + appointmentType + '\'' +
                '}';
    }
}
