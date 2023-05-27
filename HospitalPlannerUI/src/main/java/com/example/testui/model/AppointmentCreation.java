package com.example.testui.model;

import java.util.ArrayList;
import java.util.List;

public class AppointmentCreation {
    private Long doctorId;
    private Long patientId;
    private List<TimeInterval> timeIntervals;
    private String type;

    public AppointmentCreation(Long doctorId, Long patientId, List<TimeInterval> timeIntervals, String type) {
        this.doctorId = doctorId;
        this.patientId = patientId;
        this.timeIntervals = timeIntervals;
        this.type = type;
    }

    public AppointmentCreation() {
        this.timeIntervals = new ArrayList<>();
    }

    public Long getDoctorId() {
        return doctorId;
    }

    public void setDoctorId(Long doctorId) {
        this.doctorId = doctorId;
    }

    public Long getPatientId() {
        return patientId;
    }

    public void setPatientId(Long patientId) {
        this.patientId = patientId;
    }

    public List<TimeInterval> getTimeIntervals() {
        return timeIntervals;
    }

    public void setTimeIntervals(List<TimeInterval> timeIntervals) {
        this.timeIntervals = timeIntervals;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public void addTimeInterval(TimeInterval timeInterval) {
        this.timeIntervals.add(timeInterval);
    }
}
