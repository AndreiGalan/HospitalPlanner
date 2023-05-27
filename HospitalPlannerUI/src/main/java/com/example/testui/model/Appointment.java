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

    public Appointment(Long id, DoctorEntity doctorEntity, PatientEntity patientEntity, LocalDate appointmentDate, LocalTime appointmentTime, String appointmentType) {
        this.id = id;
        this.doctorEntity = doctorEntity;
        this.patientEntity = patientEntity;
        this.appointmentDate = appointmentDate;
        this.appointmentTime = appointmentTime;
        this.appointmentType = appointmentType;
    }

    public Appointment() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public DoctorEntity getDoctorEntity() {
        return doctorEntity;
    }

    public void setDoctorEntity(DoctorEntity doctorEntity) {
        this.doctorEntity = doctorEntity;
    }

    public PatientEntity getPatientEntity() {
        return patientEntity;
    }

    public void setPatientEntity(PatientEntity patientEntity) {
        this.patientEntity = patientEntity;
    }

    public LocalDate getAppointmentDate() {
        return appointmentDate;
    }

    public void setAppointmentDate(LocalDate appointmentDate) {
        this.appointmentDate = appointmentDate;
    }

    public LocalTime getAppointmentTime() {
        return appointmentTime;
    }

    public void setAppointmentTime(LocalTime appointmentTime) {
        this.appointmentTime = appointmentTime;
    }

    public String getAppointmentType() {
        return appointmentType;
    }

    public void setAppointmentType(String appointmentType) {
        this.appointmentType = appointmentType;
    }

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
