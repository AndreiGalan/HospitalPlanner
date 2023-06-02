package com.example.HospitalPlanner.service;

import com.example.HospitalPlanner.advice.exceptions.PatientNotFoundException;
import com.example.HospitalPlanner.entity.PatientEntity;
import com.example.HospitalPlanner.repo.PatientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
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

    @Override
    public PatientEntity findById(Long id) {
        return patientRepository.findById(id).orElseThrow(() -> new PatientNotFoundException(id));
    }

    public PatientEntity create(PatientEntity patientEntity) {
        System.out.println("PatientEntity: " + patientEntity);
        PatientEntity patient =  findPatient(patientEntity.getLastName(), patientEntity.getFirstName(), patientEntity.getDateOfBirth());

        if(patient == null) {
            patientRepository.save(patientEntity);
            patient = patientRepository.findPatient(patientEntity.getLastName(), patientEntity.getFirstName(), patientEntity.getDateOfBirth());
            return patient;
        }

        patientEntity.setId(patient.getId());

        saveExistingUser(patientEntity, patient);

        return patientEntity;
    }

    private void saveExistingUser(PatientEntity patientEntity,PatientEntity patientFromDb) {
        patientRepository.save(PatientEntity.builder()
                .id(patientEntity.getId())
                .firstName(patientEntity.getFirstName())
                .lastName(patientEntity.getLastName())
                .dateOfBirth(patientEntity.getDateOfBirth())
                .phoneNumber(patientEntity.getPhoneNumber() == null ? patientEntity.getPhoneNumber() : patientFromDb.getPhoneNumber())
                .address(patientEntity.getAddress() == null ? patientEntity.getAddress() : patientFromDb.getAddress())
                .email(patientEntity.getEmail() ==null ? patientEntity.getEmail() : patientFromDb.getEmail())
                .build());
    }

    public PatientEntity findPatient(String lastName, String firstName, LocalDate birthDate) {

        if(lastName == null || firstName == null || birthDate == null) {
            throw new RuntimeException("Invalid arguments");
        }

        return patientRepository.findPatient(lastName, firstName, birthDate);
    }

}
