package com.example.HospitalPlanner.service;

import com.example.HospitalPlanner.entity.DoctorEntity;
import com.example.HospitalPlanner.entity.PatientEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface PatientService {
    List<PatientEntity> findAll();
    void delete(Long id);
}
