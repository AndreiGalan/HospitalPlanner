package com.example.testui.fetchControllers;

import com.example.testui.model.PatientEntity;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;

import java.io.IOException;
import java.util.concurrent.CompletableFuture;

public class PatientFetchController {
    public PatientEntity registerPatient(PatientEntity patientEntity) {
        CompletableFuture<PatientEntity> completableFuture = CompletableFuture.supplyAsync(() -> {
            try {
                ObjectMapper objectMapper = new ObjectMapper();
                objectMapper.registerModule(new JavaTimeModule());
                String appointmentJson = objectMapper.writeValueAsString(patientEntity);

                HttpResponse<String> response = Unirest.post("http://localhost:8199/patients/create")
                        .header("Content-Type", "application/json")
                        .body(appointmentJson)
                        .asString();


                if (response.getStatus() == 201 || response.getStatus() == 200) {
                    PatientEntity createdPatient = objectMapper.readValue(response.getBody(), PatientEntity.class);
                    System.out.println("Appointment created successfully.");
                    return createdPatient;
                } else {
                    System.out.println("Failed to register patient. Response code: " + response.getStatus());
                    return null;
                }

            } catch (IOException | UnirestException e) {
                throw new RuntimeException(e);
            }
        });
        return completableFuture.join();
    }
}
