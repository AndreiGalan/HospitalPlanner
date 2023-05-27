package com.example.HospitalPlanner.controllers;

import com.example.HospitalPlanner.entity.DoctorEntity;
import com.example.HospitalPlanner.entity.PatientEntity;
import com.example.HospitalPlanner.service.DoctorService;
import com.example.HospitalPlanner.service.PatientService;
import org.springframework.beans.factory.annotation.Autowired;
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
    public List<PatientEntity> findAll() {
        return patientService.findAll();
    }

    @GetMapping("/{id}")
    public PatientEntity findById(@PathVariable("id") Long id) {
        return patientService.findById(id);
    }

    @PostMapping("/create")
    public PatientEntity create(@RequestBody PatientEntity patientEntity) {
        return patientService.create(patientEntity);
    }

    @GetMapping("/find")
    public PatientEntity findPatient(@RequestBody PatientEntity patientEntity) {
        return patientService.findPatient(patientEntity.getLastName(), patientEntity.getFirstName(), patientEntity.getDateOfBirth());
    }
    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        patientService.delete(id);
    }
}
