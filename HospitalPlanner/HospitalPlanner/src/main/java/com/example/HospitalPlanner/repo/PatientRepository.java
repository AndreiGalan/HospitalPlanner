package com.example.HospitalPlanner.repo;

import com.example.HospitalPlanner.entity.DoctorEntity;
import com.example.HospitalPlanner.entity.PatientEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface PatientRepository extends JpaRepository<PatientEntity, Long>{

    @Query(value = "SELECT * FROM patients", nativeQuery = true)
    List<PatientEntity> getAllPatients();


    @Query(value = "SELECT * FROM patients WHERE last_name = ?1 AND first_name = ?2 AND date_of_birth = ?3", nativeQuery = true)
    PatientEntity findPatient(String lastName, String firstName, LocalDate birthDate);

}
