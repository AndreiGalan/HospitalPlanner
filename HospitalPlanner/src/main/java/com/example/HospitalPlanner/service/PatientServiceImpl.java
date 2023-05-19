package com.example.HospitalPlanner.service;

import com.example.HospitalPlanner.entity.DoctorEntity;
import com.example.HospitalPlanner.entity.PatientEntity;
import com.example.HospitalPlanner.repo.DoctorRepository;
import com.example.HospitalPlanner.repo.PatientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PatientServiceImpl implements PatientService {

    private final PatientRepository patientRepository;

    @Autowired
    public PatientServiceImpl(PatientRepository patientRepository) {
        this.patientRepository = patientRepository;
    }

    @Override
    public List<PatientEntity> findAll() {
        List<PatientEntity> patientEntities = patientRepository.findAll();
        System.out.println(patientEntities);
        return patientEntities;
    }
    public void delete(Long id) {
        patientRepository.deleteById(id);
    }
}
