package com.example.HospitalPlanner.advice.exceptions;

public class DoctorNotFoundException extends EntityNotFoundException{
    public DoctorNotFoundException() {
        super("doctor");
    }
    public DoctorNotFoundException(Long id) {
        super("doctor", id);
    }
}
