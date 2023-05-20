package com.example.HospitalPlanner.service;

import com.example.HospitalPlanner.dto.AppointmentCreationDto;
import com.example.HospitalPlanner.entity.Appointment;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface AppointmentService {
    List<Appointment> findAll();
    Appointment findById(Long id);
    void create(AppointmentCreationDto appointmentCreationDto);
    List<Appointment> findByDoctorId(Long Id);
    List<Appointment> findByPatientId(Long Id);
    void delete(Long id);
}
