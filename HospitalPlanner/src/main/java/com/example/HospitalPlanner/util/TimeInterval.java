package com.example.HospitalPlanner.util;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TimeInterval {
    private LocalDate date ;
    private LocalTime start;
    private LocalTime end;

    @Override
    public String toString() {
        return "TimeInterval{" +
                "date=" + date +
                ", start=" + start +
                ", end=" + end +
                '}' + "\n";
    }

}





