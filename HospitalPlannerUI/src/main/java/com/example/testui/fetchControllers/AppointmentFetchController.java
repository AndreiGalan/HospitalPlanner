package com.example.testui.fetchControllers;

import com.example.testui.model.AppointmentCreation;
import com.example.testui.model.PatientEntity;
import com.example.testui.model.TimeInterval;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;

import java.io.IOException;
import java.util.concurrent.CompletableFuture;

public class AppointmentFetchController {
    public TimeInterval createAppointment(AppointmentCreation appointment) {
        CompletableFuture<TimeInterval> completableFuture = CompletableFuture.supplyAsync(() -> {
            try {
                ObjectMapper objectMapper = new ObjectMapper();
                objectMapper.registerModule(new JavaTimeModule());
                String appointmentJson = objectMapper.writeValueAsString(appointment);
                HttpResponse<String> response = Unirest.post("http://localhost:8199/apps/create")
                        .header("Content-Type", "application/json")
                        .body(appointmentJson)
                        .asString();

                if (response.getStatus() == 201 || response.getStatus() == 200) {
                    TimeInterval timeInterval = objectMapper.readValue(response.getBody(), TimeInterval.class);
                    System.out.println("Appointment created successfully.");
                    return timeInterval;
                } else {
                    throw new IOException("Request failed with response code: " + response.getStatus());
                }
            } catch (IOException | UnirestException e) {
                throw new RuntimeException(e);
            }
        });
        completableFuture.exceptionally(ex -> {
            System.out.println("Failed to create appointment: " + ex.getMessage());
            return null;
        });
        return completableFuture.join();
    }
}
