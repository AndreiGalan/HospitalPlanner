package com.example.HospitalPlanner.repo;

import com.example.HospitalPlanner.entity.DoctorEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DoctorRepository extends JpaRepository<DoctorEntity, Long>{

    @Query(value = "SELECT * FROM doctors", nativeQuery = true)
    List<DoctorEntity> getAllDoctors();

    @Query(value = "SELECT DISTINCT specialization FROM doctors;", nativeQuery = true)
    List<String> getAllSpecializations();


}
