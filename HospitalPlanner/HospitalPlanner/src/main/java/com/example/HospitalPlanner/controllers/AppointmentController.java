package com.example.HospitalPlanner.controllers;

import com.example.HospitalPlanner.dto.AppointmentCreationDto;
import com.example.HospitalPlanner.entity.Appointment;
import com.example.HospitalPlanner.service.AppointmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/apps")
public class AppointmentController {
    private final AppointmentService appointmentService;

    @Autowired
    public AppointmentController(AppointmentService appointmentService) {
        this.appointmentService = appointmentService;
    }

    @GetMapping(value = "", produces = "application/json")
    public ResponseEntity<List<Appointment>> findAll() {
        System.out.println("find all");
        List<Appointment> appointments = appointmentService.findAll();
        return ResponseEntity.ok(appointments);
    }

    @GetMapping(value = "/doctor/{id}", produces = "application/json")
    public ResponseEntity<List<Appointment>> findByDoctorId(@PathVariable("id") Long id) {
        List<Appointment> appointments = appointmentService.findByDoctorId(id);
        return ResponseEntity.ok(appointments);
    }

    @GetMapping(value = "/patient/{id}", produces = "application/json")
    public ResponseEntity<List<Appointment>> findByPatientId(@PathVariable("id") Long id) {
        List<Appointment> appointments = appointmentService.findByPatientId(id);
        return ResponseEntity.ok(appointments);
    }

    @GetMapping(value = "/id", produces = "application/json")
    public ResponseEntity<Appointment> findById(@PathVariable("id") Long id) {
        Appointment appointment = appointmentService.findById(id);
        return ResponseEntity.ok(appointment);
    }

    @PostMapping(value = "/create", produces = "application/json")
    public void create(@RequestBody AppointmentCreationDto appointmentCreationDto) {
        System.out.println("create");
        appointmentService.create(appointmentCreationDto);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        appointmentService.delete(id);
    }

}
