package com.example.HospitalPlanner.advice.exceptions;

public class EntityNotFoundException extends RuntimeException{
    public EntityNotFoundException(String message) {
        super(message+" not found");
    }
    public EntityNotFoundException(String message, Long id) {
        super(message + " with id: " + id + " not found");
    }



}
