package com.example.HospitalPlanner.service;

import com.example.HospitalPlanner.entity.DoctorEntity;
import com.example.HospitalPlanner.entity.PatientEntity;
import com.example.HospitalPlanner.repo.DoctorRepository;
import com.example.HospitalPlanner.repo.PatientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

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

    @Override
    public PatientEntity findById(Long id) {
        PatientEntity patientEntity = patientRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Patient not found"));
        return patientEntity;
    }

    public PatientEntity create(PatientEntity patientEntity) {
        System.out.println("PatientEntity: " + patientEntity);
        PatientEntity patient =  findPatient(patientEntity.getLastName(), patientEntity.getFirstName(), patientEntity.getDateOfBirth());

        if(patient == null) {
            patientRepository.save(patientEntity);
            patient = patientRepository.findPatient(patientEntity.getLastName(), patientEntity.getFirstName(), patientEntity.getDateOfBirth());
        }
        System.out.println("Patient: " + patient);
        return patient;
    }

    public PatientEntity findPatient(String lastName, String firstName, LocalDate birthDate) {
        PatientEntity patientEntity = Optional.ofNullable(patientRepository.findPatient(lastName, firstName, birthDate)).orElseThrow(() -> new IllegalArgumentException("Patient not found"));
        return patientEntity;
    }

}
