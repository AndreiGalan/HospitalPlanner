package com.example.HospitalPlanner.repo;

import com.example.HospitalPlanner.entity.DoctorEntity;
import com.example.HospitalPlanner.entity.PatientEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PatientRepository extends JpaRepository<PatientEntity, Long>{

    @Query(value = "SELECT * FROM patients", nativeQuery = true)
    List<PatientEntity> getAllPatients();


}
