package com.example.HospitalPlanner.controllers;

import com.example.HospitalPlanner.entity.DoctorEntity;
import com.example.HospitalPlanner.service.DoctorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/doctors")
public class DoctorController {
    private final DoctorService doctorService;

    @Autowired
    public DoctorController(DoctorService doctorService) {
        this.doctorService = doctorService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<DoctorEntity> findById(@PathVariable Long id) {
        return ResponseEntity.ok(doctorService.findById(id));
    }

    @GetMapping("")
    public ResponseEntity<List<DoctorEntity>> findAll() {
        return ResponseEntity.ok(doctorService.findAll());
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<Void>  delete(@PathVariable Long id) {
        doctorService.delete(id);
        return ResponseEntity.noContent().build() ;
    }

    @GetMapping("/specializations")
    public ResponseEntity<List<String>> getAllSpecializations() {
        return ResponseEntity.ok(doctorService.getAllSpecializations());
    }
}
