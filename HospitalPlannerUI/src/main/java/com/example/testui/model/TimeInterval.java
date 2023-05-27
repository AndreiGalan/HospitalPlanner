package com.example.testui.model;

import java.time.LocalDate;
import java.time.LocalTime;

public class TimeInterval {
    private LocalDate date ;
    private LocalTime start;
    private LocalTime end;

    public TimeInterval(LocalDate date, LocalTime start, LocalTime end) {
        this.date = date;
        this.start = start;
        this.end = end;
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

    @Override
    public String toString() {
        return "TimeInterval{" +
                "date=" + date +
                ", start=" + start +
                ", end=" + end +
                '}' + "\n";
    }
}
