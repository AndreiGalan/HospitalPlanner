package com.example.HospitalPlanner.util;

public enum AppointmentType {
    CONSULTATION("consultation",20),
    SURGERY("surgery",60),
    THERAPY("therapy",100);

    private int value;
    private String name;
    AppointmentType( String name,int value) {
        this.value = value;
        this.name = name;
    }
    public int getValue() {
        return value;
    }

    public String getName() {
        return name;
    }

    public static AppointmentType getType(String text) {
        for (AppointmentType b : AppointmentType.values()) {
            if (b.name.equalsIgnoreCase(text)) {
                return b;
            }
        }
        return null;
    }
}
