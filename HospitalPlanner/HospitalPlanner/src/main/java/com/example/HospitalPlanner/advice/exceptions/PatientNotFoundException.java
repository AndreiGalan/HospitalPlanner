package com.example.HospitalPlanner.advice.exceptions;

public class PatientNotFoundException extends EntityNotFoundException {
    public PatientNotFoundException() {
        super("patient");
    }
    public PatientNotFoundException(Long id) {
        super("patient", id);
    }
}
