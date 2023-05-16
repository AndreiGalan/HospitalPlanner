package com.example.HospitalPlanner.controllers;

import com.example.HospitalPlanner.dto.AppointmentCreationDto;
import com.example.HospitalPlanner.entity.Appointment;
import com.example.HospitalPlanner.service.AppointmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/apps")
public class AppointmentController {
    private final AppointmentService appointmentService;

    @Autowired
    public AppointmentController(AppointmentService appointmentService) {
        this.appointmentService = appointmentService;
    }

    @GetMapping("")
    public ResponseEntity<List<Appointment>> findAll() {
        List<Appointment> appointments = appointmentService.findAll();
        return ResponseEntity.ok(appointments);
    }

    @GetMapping("/doctor/id")
    public ResponseEntity<List<Appointment>> findByDoctorId(Long id) {
        List<Appointment> appointments = appointmentService.findByDoctorId(id);
        return ResponseEntity.ok(appointments);
    }

    @GetMapping("/patient/id")
    public ResponseEntity<List<Appointment>> findByPatientId(Long id) {
        List<Appointment> appointments = appointmentService.findByPatientId(id);
        return ResponseEntity.ok(appointments);
    }

    @GetMapping("/id")
    public ResponseEntity<Appointment> findById(Long id) {
        Appointment appointment = appointmentService.findById(id);
        return ResponseEntity.ok(appointment);
    }
    @PostMapping("/create")
    public ResponseEntity<Void> create(AppointmentCreationDto appointmentCreationDto) {
        appointmentService.create(appointmentCreationDto);
        return ResponseEntity.noContent().build();
    }

}
