package com.example.testui.fetchControllers;

import com.example.testui.model.DoctorEntity;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.CompletableFuture;

public class DoctorFetchController {
    public List<DoctorEntity> getDoctors() {
        CompletableFuture<List<DoctorEntity>> completableFuture =  CompletableFuture.supplyAsync(() -> {
            try {
                HttpResponse<String> response = Unirest.get("http://localhost:8199/doctors")
                        .header("accept", "application/json")
                        .asString();

                if (response.getStatus() == 200) {
                    String responseBody = response.getBody();
                    ObjectMapper objectMapper = new ObjectMapper();
                    objectMapper.registerModule(new JavaTimeModule());
                    //System.out.println(responseBody);
                    List<DoctorEntity> doctorEntityList = objectMapper.readValue(responseBody, new TypeReference<List<DoctorEntity>>() {
                    });

                    return doctorEntityList;
                } else {
                    throw new IOException("Request failed with response code: " + response.getStatus());
                }
            } catch (IOException | UnirestException e) {
                throw new RuntimeException(e);
            }
        });
        return completableFuture.join();
    }

    public List<String> getAllSpecializations(){
        CompletableFuture<List<String>> completableFuture =  CompletableFuture.supplyAsync(() -> {
            try {
                HttpResponse<String> response = Unirest.get("http://localhost:8199/doctors/specializations")
                        .header("accept", "application/json")
                        .asString();

                if (response.getStatus() == 200) {
                    String responseBody = response.getBody();
                    ObjectMapper objectMapper = new ObjectMapper();
                    objectMapper.registerModule(new JavaTimeModule());
                    //System.out.println(responseBody);
                    List<String> specializations = objectMapper.readValue(responseBody, new TypeReference<List<String>>() {
                    });

                    return specializations;
                } else {
                    throw new IOException("Request failed with response code: " + response.getStatus());
                }
            } catch (IOException | UnirestException e) {
                throw new RuntimeException(e);
            }
        });
        return completableFuture.join();
    }

    public List<DoctorEntity> getDoctorsBySpecialization(String specialization) {
        CompletableFuture<List<DoctorEntity>> completableFuture =  CompletableFuture.supplyAsync(() -> {
            try {
                HttpResponse<String> response = Unirest.get("http://localhost:8199/doctors")
                        .header("accept", "application/json")
                        .asString();

                if (response.getStatus() == 200) {
                    String responseBody = response.getBody();
                    ObjectMapper objectMapper = new ObjectMapper();
                    objectMapper.registerModule(new JavaTimeModule());
                    //System.out.println(responseBody);
                    List<DoctorEntity> doctorEntityList = objectMapper.readValue(responseBody, new TypeReference<List<DoctorEntity>>() {
                    });

                    if(specialization != null && !specialization.isEmpty()){
                        doctorEntityList.removeIf(doctorEntity -> !doctorEntity.getSpecialization().equals(specialization));
                    }

                    return doctorEntityList;
                } else {
                    throw new IOException("Request failed with response code: " + response.getStatus());
                }
            } catch (IOException | UnirestException e) {
                throw new RuntimeException(e);
            }
        });
        return completableFuture.join();
    }

    public List<DoctorEntity> getDoctorsByFilter(String name, String specialization){
        CompletableFuture<List<DoctorEntity>> completableFuture =  CompletableFuture.supplyAsync(() -> {
            try {
                HttpResponse<String> response = Unirest.get("http://localhost:8199/doctors")
                        .header("accept", "application/json")
                        .asString();

                if (response.getStatus() == 200) {
                    String responseBody = response.getBody();
                    ObjectMapper objectMapper = new ObjectMapper();
                    objectMapper.registerModule(new JavaTimeModule());
                    //System.out.println(responseBody);
                    List<DoctorEntity> doctorEntityList = objectMapper.readValue(responseBody, new TypeReference<List<DoctorEntity>>() {
                    });

                    if(name != null && !name.isEmpty()){
                        if (name.contains(" ")) {
                            //remove only first space
                            String[] names = name.split(" ", 2);
                            doctorEntityList.removeIf(doctorEntity -> !doctorEntity.getFirstName().contains(names[0]) || !doctorEntity.getLastName().contains(names[1]));
                        } else {
                            doctorEntityList.removeIf(doctorEntity -> !doctorEntity.getFirstName().equals(name) && !doctorEntity.getLastName().equals(name));
                        }
                    }

                    if(specialization != null && !specialization.isEmpty()){
                        doctorEntityList.removeIf(doctorEntity -> !doctorEntity.getSpecialization().equals(specialization));
                    }

                    return doctorEntityList;
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
