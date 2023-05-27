package com.example.testui.model;


import java.time.LocalTime;

public class DoctorEntity {
    private Long id;
    private String firstName;
    private String lastName;
    private String specialization;
    private LocalTime programStart;
    private LocalTime programEnd;

    public DoctorEntity() {
    }

    public DoctorEntity(Long id, String firstName, String lastName, String specialization, LocalTime programStart, LocalTime programEnd) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.specialization = specialization;
        this.programStart = programStart;
        this.programEnd = programEnd;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getSpecialization() {
        return specialization;
    }

    public void setSpecialization(String specialization) {
        this.specialization = specialization;
    }

    public LocalTime getProgramStart() {
        return programStart;
    }

    public void setProgramStart(LocalTime programStart) {
        this.programStart = programStart;
    }

    public LocalTime getProgramEnd() {
        return programEnd;
    }

    public void setProgramEnd(LocalTime programEnd) {
        this.programEnd = programEnd;
    }

    @Override
    public String toString() {
        return firstName + ' ' +
                lastName + ' ' +
                ", specialization='" + specialization + '\'' +
                ", programStart=" + programStart +
                ", programEnd=" + programEnd +
                '}';
    }
}