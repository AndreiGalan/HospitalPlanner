package com.example.HospitalPlanner.dto;

import com.example.HospitalPlanner.util.TimeInterval;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component

@Data
@NoArgsConstructor
@Builder
@Getter
public class AppointmentCreationDto {

    private Long doctorId;
    private Long patientId;
    private List<TimeInterval> timeIntervals;
    private String type;

    public AppointmentCreationDto(Long doctorId, Long patientId, List<TimeInterval> timeIntervals, String type) {
        this.doctorId = doctorId;
        this.patientId = patientId;
        this.timeIntervals = timeIntervals;
        this.type = type;
    }
}


