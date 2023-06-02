package com.example.HospitalPlanner.advice.exceptions;

public class AppointmentNotFoundException extends EntityNotFoundException{
    public AppointmentNotFoundException() {
        super("appointment");
    }

    public AppointmentNotFoundException(Long id) {
        super("appointment", id);
    }
}
