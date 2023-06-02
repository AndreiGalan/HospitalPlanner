package com.example.HospitalPlanner.service;

import com.example.HospitalPlanner.entity.DoctorEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface DoctorService {
    List<DoctorEntity> findAll();
    void delete(Long id);
    List<String> getAllSpecializations();

    DoctorEntity findById(Long id);
}
