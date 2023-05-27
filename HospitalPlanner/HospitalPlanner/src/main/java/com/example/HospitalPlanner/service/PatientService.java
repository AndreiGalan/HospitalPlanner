package com.example.HospitalPlanner.service;

import com.example.HospitalPlanner.entity.DoctorEntity;
import com.example.HospitalPlanner.entity.PatientEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public interface PatientService {
    List<PatientEntity> findAll();
    void delete(Long id);
    PatientEntity findById(Long id);
    PatientEntity create(PatientEntity patientEntity);
    PatientEntity findPatient(String lastName, String firstName, LocalDate birthDate);
}
