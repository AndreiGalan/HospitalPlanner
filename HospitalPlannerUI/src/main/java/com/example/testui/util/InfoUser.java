package com.example.testui.util;

import com.example.testui.model.PatientEntity;

import java.time.LocalDate;

public class InfoUser {
    private static Long id;
    private static String username;
    private static String lastName;
    private static String firstName;
    private static String email;
    private static String phoneNumber;
    private static String address;
    private static LocalDate dateOfBirth;

    private static boolean isCreated = false;

    public static void setInfo(PatientEntity patientEntity) {
        setId(patientEntity.getId());
        setFirstName(patientEntity.getFirstName());
        setLastName(patientEntity.getLastName());
        setDateOfBirth(patientEntity.getDateOfBirth());
        setAddress(patientEntity.getAddress());
        setEmail(patientEntity.getEmail());
        setPhoneNumber(patientEntity.getPhoneNumber());

        isCreated = true;
    }
    public static boolean isCreated() {
        return isCreated;
    }
    public static void setCreated(boolean isCreated) {
        InfoUser.isCreated = isCreated;
    }
    public static Long getId() {
        return id;
    }

    public static void setId(Long id) {
        InfoUser.id = id;
    }

    public static String getUsername() {
        return username;
    }

    public static void setUsername(String username) {
        InfoUser.username = username;
    }

    public static String getLastName() {
        return lastName;
    }

    public static void setLastName(String lastName) {
        InfoUser.lastName = lastName;
    }

    public static String getFirstName() {
        return firstName;
    }

    public static void setFirstName(String firstName) {
        InfoUser.firstName = firstName;
    }

    public static String getEmail() {
        return email;
    }

    public static void setEmail(String email) {
        InfoUser.email = email;
    }

    public static String getPhoneNumber() {
        return phoneNumber;
    }

    public static void setPhoneNumber(String phoneNumber) {
        InfoUser.phoneNumber = phoneNumber;
    }

    public static String getAddress() {
        return address;
    }

    public static void setAddress(String address) {
        InfoUser.address = address;
    }

    public static LocalDate getDateOfBirth() {
        return dateOfBirth;
    }

    public static void setDateOfBirth(LocalDate dateOfBirth) {
        InfoUser.dateOfBirth = dateOfBirth;
    }
}