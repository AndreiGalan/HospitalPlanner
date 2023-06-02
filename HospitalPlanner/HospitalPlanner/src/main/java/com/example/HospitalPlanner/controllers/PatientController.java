package com.example.HospitalPlanner.controllers;

import com.example.HospitalPlanner.entity.DoctorEntity;
import com.example.HospitalPlanner.entity.PatientEntity;
import com.example.HospitalPlanner.service.DoctorService;
import com.example.HospitalPlanner.service.PatientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/patients")
public class PatientController {
    private final PatientService patientService;

    @Autowired
    public PatientController(PatientService patientService) {
        this.patientService = patientService;
    }

    @GetMapping("")
    public ResponseEntity<List<PatientEntity>>findAll() {
        return ResponseEntity.ok( patientService.findAll());
    }

    @GetMapping("/{id}")
    public  ResponseEntity<PatientEntity> findById(@PathVariable("id") Long id) {
        return ResponseEntity.ok(patientService.findById(id));
    }

    @PostMapping("/create")
    public ResponseEntity<PatientEntity> create(@RequestBody PatientEntity patientEntity) {
        return ResponseEntity.ok(patientService.create(patientEntity)) ;
    }

    @GetMapping("/find")
    public ResponseEntity<PatientEntity> findPatient(@RequestBody PatientEntity patientEntity) {
        return ResponseEntity.ok(patientService.findPatient(patientEntity.getLastName(), patientEntity.getFirstName(), patientEntity.getDateOfBirth()));
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        patientService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
