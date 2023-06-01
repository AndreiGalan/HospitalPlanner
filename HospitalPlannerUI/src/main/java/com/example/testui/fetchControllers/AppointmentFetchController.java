package com.example.testui.fetchControllers;

import com.example.testui.Exceptions.DoctorOperationException;
import com.example.testui.model.*;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;
import javafx.collections.ObservableList;

import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
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
                    String errorMessage = response.getStatus() == 400 ? "Doctor is not available at this time" : "Doctor is busy at this time";
                    System.out.println(errorMessage);
                    throw new DoctorOperationException(errorMessage);
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

    public List<Appointment> getPatientAppointments(Long patientId) {
        CompletableFuture<List<Appointment>> completableFuture = CompletableFuture.supplyAsync(() -> {
            try {
                HttpResponse<String> response = Unirest.get("http://localhost:8199/apps/patient/" + patientId)
                        .header("accept", "application/json")
                        .asString();

                if (response.getStatus() == 200) {
                    String responseBody = response.getBody();
                    ObjectMapper objectMapper = new ObjectMapper();
                    objectMapper.registerModule(new JavaTimeModule());
                    //System.out.println(responseBody);
                    List<Appointment> appointmentList = objectMapper.readValue(responseBody, new com.fasterxml.jackson.core.type.TypeReference<List<Appointment>>() {
                    });

                    return appointmentList;
                } else {
                    throw new IOException("Request failed with response code: " + response.getStatus());
                }
            } catch (IOException | UnirestException e) {
                throw new RuntimeException(e);
            }
        });
        return completableFuture.join();
    }

    public List<TimeInterval> getDoctorAppointments(Long doctorId, LocalDate date) {
        CompletableFuture<List<TimeInterval>> completableFuture = CompletableFuture.supplyAsync(() -> {
            try {
                HttpResponse<String> response = Unirest.get("http://localhost:8199/apps/doctor/" + doctorId)
                        .header("accept", "application/json")
                        .asString();

                if (response.getStatus() == 200) {
                    String responseBody = response.getBody();
                    ObjectMapper objectMapper = new ObjectMapper();
                    objectMapper.registerModule(new JavaTimeModule());
                    List<Appointment> appointmentList = objectMapper.readValue(responseBody, new com.fasterxml.jackson.core.type.TypeReference<List<Appointment>>() {});

                    System.out.println("Date: " + appointmentList);

                    if(date != null) {
                        appointmentList.removeIf(appointment -> !appointment.getAppointmentDate().equals(date));
                    }

                    List<TimeInterval> timeIntervalList = new ArrayList<>();
                    for (Appointment appointment : appointmentList) {
                        LocalTime endTime;
                        System.out.println("Appointment type: " + appointment.getAppointmentType());
                        switch (appointment.getAppointmentType()) {
                            case "consultation":
                                endTime = appointment.getAppointmentTime().plusMinutes(20);
                                break;
                            case "surgery":
                                endTime = appointment.getAppointmentTime().plusMinutes(60);
                                break;
                            case "therapy":
                                endTime = appointment.getAppointmentTime().plusMinutes(100);
                                break;
                            case "vaccination":
                                endTime = appointment.getAppointmentTime().plusMinutes(10);
                            default:
                                endTime = appointment.getAppointmentTime();
                        }
                        TimeInterval timeInterval = new TimeInterval(appointment.getAppointmentDate(), appointment.getAppointmentTime(), endTime, appointment.getAppointmentType());

                        timeIntervalList.add(timeInterval);
                    }

                    System.out.println("Time interval list: " + timeIntervalList);
                    return timeIntervalList;
                } else {
                    throw new IOException("Request failed with response code: " + response.getStatus());
                }
            } catch (IOException | UnirestException e) {
                throw new RuntimeException(e);
            }
        });
        return completableFuture.join();
    }

}
