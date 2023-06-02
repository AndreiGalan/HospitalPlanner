package com.example.HospitalPlanner.advice.exceptions;

public class DoctorIsBusyException extends RuntimeException {
    public DoctorIsBusyException(String message ) {
        super(message);
    }
}
