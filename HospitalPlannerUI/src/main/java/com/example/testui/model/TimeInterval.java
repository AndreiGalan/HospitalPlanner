package com.example.testui.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.time.LocalDate;
import java.time.LocalTime;

@JsonIgnoreProperties(ignoreUnknown = true)
public class TimeInterval {
    private LocalDate date ;
    private LocalTime start;
    private LocalTime end;
    private String type;

    public TimeInterval(LocalDate date, LocalTime start, LocalTime end, String type) {
        this.date = date;
        this.start = start;
        this.end = end;
        this.type = type;
    }

    public TimeInterval() {
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public LocalTime getStart() {
        return start;
    }

    public void setStart(LocalTime start) {
        this.start = start;
    }

    public LocalTime getEnd() {
        return end;
    }

    public void setEnd(LocalTime end) {
        this.end = end;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    @Override
    public String toString() {
        return "TimeInterval{" +
                "date=" + date +
                ", start=" + start +
                ", end=" + end +
                ", type='" + type + '\'' +
                '}' + "\n";
    }
}
