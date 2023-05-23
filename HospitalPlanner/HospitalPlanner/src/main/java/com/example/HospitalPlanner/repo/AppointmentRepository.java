package com.example.HospitalPlanner.repo;

import com.example.HospitalPlanner.entity.Appointment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface AppointmentRepository extends JpaRepository<Appointment, Long> {

    @Query(value = "SELECT * FROM appointments where doctor_id = :id", nativeQuery = true)
    List<Appointment> findByDoctorId(@Param(value = "id")Long Id);

    @Query(value = "SELECT * FROM appointments where patient_id = :id", nativeQuery = true)
    List<Appointment> findByPatientId(@Param(value = "id") Long Id);

    @Query(value = "SELECT * FROM appointments where id = :id", nativeQuery = true)
    Optional<Appointment> findById(@Param(value = "id") Long Id);
}
