package com.example.HospitalPlanner.service;

import com.example.HospitalPlanner.advice.exceptions.DoctorNotFoundException;
import com.example.HospitalPlanner.advice.exceptions.EntityNotFoundException;
import com.example.HospitalPlanner.entity.DoctorEntity;
import com.example.HospitalPlanner.repo.DoctorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DoctorServiceImpl implements DoctorService {

    private final DoctorRepository doctorRepository;

    @Autowired
    public DoctorServiceImpl(DoctorRepository doctorRepository) {
        this.doctorRepository = doctorRepository;
    }

    @Override
    public List<DoctorEntity> findAll() {
        List<DoctorEntity> doctorEntities = doctorRepository.findAll();
        System.out.println(doctorEntities);
        return doctorEntities;
    }

    public DoctorEntity findById(Long id) {
        return doctorRepository.findById(id).orElseThrow(()->new DoctorNotFoundException(id));
    }
    public void delete(Long id) {
        doctorRepository.findById(id).orElseThrow(()->new DoctorNotFoundException(id));
        doctorRepository.deleteById(id);
    }

    @Override
    public List<String> getAllSpecializations() {
        return doctorRepository.getAllSpecializations();
    }
}
