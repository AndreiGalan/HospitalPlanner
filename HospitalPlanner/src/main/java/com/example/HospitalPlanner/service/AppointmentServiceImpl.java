package com.example.HospitalPlanner.service;

import com.example.HospitalPlanner.dto.AppointmentCreationDto;
import com.example.HospitalPlanner.entity.Appointment;
import com.example.HospitalPlanner.entity.DoctorEntity;
import com.example.HospitalPlanner.repo.AppointmentRepository;
import com.example.HospitalPlanner.repo.DoctorRepository;
import com.example.HospitalPlanner.util.AppointmentType;
import com.example.HospitalPlanner.util.TimeInterval;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;

@Service
public class AppointmentServiceImpl implements AppointmentService {

    private final AppointmentRepository appointmentRepository;
    private final DoctorRepository doctorRepository;

    @Autowired
    public AppointmentServiceImpl(AppointmentRepository appointmentRepository, DoctorRepository doctorRepository) {
        this.appointmentRepository = appointmentRepository;
        this.doctorRepository = doctorRepository;
    }


    @Override
    public List<Appointment> findAll() {
        List<Appointment> appointments = Optional.ofNullable( appointmentRepository.findAll()).orElse(new ArrayList<>());
        return appointments;
    }

    @Override
    public Appointment findById(Long id) {
        Appointment appointment = appointmentRepository.findById(id).orElse(null); // TODO - THROW EXCEPTION
        return appointment;
    }

    @Override
    public void create(AppointmentCreationDto appointmentCreationDto) {
        Integer durationOfAppointment = AppointmentType.valueOf(appointmentCreationDto.getType()).getValue();

        DoctorEntity doctor = doctorRepository.findById(appointmentCreationDto.getDoctorId()).orElse(null); // TODO - THROW CUSTOM EXCEPTION

        List<Appointment> appointmentsDoctor = findByDoctorId(appointmentCreationDto.getDoctorId()).stream()
                .filter(appointment -> appointmentCreationDto.getTimeIntervals().stream()
                        .anyMatch(timeInterval -> appointment.getAppointmentDate().equals(timeInterval.getDate())))
                .toList();

        for (TimeInterval timeInterval : appointmentCreationDto.getTimeIntervals()) {
            List<Appointment> busyAppointments = appointmentsDoctor.stream() // TODO - IMPORT FROM REPO BY DATE AND DOCTOR ID
                    .filter(appointment -> appointment.getAppointmentDate().equals(timeInterval.getDate()))
                    .sorted(Comparator.comparing(Appointment::getAppointmentTime))
                    .toList();

            //TODO - DISCUTIE despre cum sa facem cu intervalele care sunt la aceeasi data dar nu se suprapun
            generateFreeIntervals(busyAppointments,doctor,durationOfAppointment);
        }


//
//        if(appointmentsDoctor == null || appointmentsDoctor.isEmpty()) {
//
//        }
//
//        if (appointmentsDoctor.size() > 0) {
//            for (Appointment appointment1 : appointmentsDoctor) {
//                if () {
//                    throw new RuntimeException("Doctor is busy at this time");
//                }
//            }
//        }

        appointmentRepository.save(new Appointment());

    }

    private List<TimeInterval> generateFreeIntervals(List<Appointment> busyAppointments,DoctorEntity doctor, Integer duration) {
        List<TimeInterval> freeTimeIntervals = new ArrayList<>();

        // intre inceperea programului si primului appointment
        LocalTime programStart = doctor.getProgramStart();
        LocalTime firstBusyAppointmentTime = busyAppointments.get(0).getAppointmentTime();
        if (Duration.between(programStart, firstBusyAppointmentTime).toMinutes() >= duration) {
            TimeInterval freeInterval = TimeInterval.builder()
                    .date(busyAppointments.get(0).getAppointmentDate())
                    .start(programStart)
                    .end(firstBusyAppointmentTime)
                    .build();
            freeTimeIntervals.add(freeInterval);
        }

        // intre fiecare 2 appointments
        for (int i = 0; i < busyAppointments.size() - 1; i++) {
            Appointment currentAppointment = busyAppointments.get(i);
            Appointment nextAppointment = busyAppointments.get(i + 1);

            Integer durationOfCurrentAppointment = AppointmentType.valueOf(currentAppointment.getAppointmentType().toUpperCase()).getValue();

            LocalTime completedCurrentAppointmentTime = currentAppointment.getAppointmentTime().plusMinutes(durationOfCurrentAppointment);
            LocalTime nextAppointmentTime = nextAppointment.getAppointmentTime();

            if (Duration.between(completedCurrentAppointmentTime, nextAppointmentTime).toMinutes()  >= duration) {
                TimeInterval freeInterval = new TimeInterval();
                freeInterval.setDate(currentAppointment.getAppointmentDate());
                freeInterval.setStart(completedCurrentAppointmentTime);
                freeInterval.setEnd(nextAppointmentTime);
                freeTimeIntervals.add(freeInterval);
            }
        }

        // intre ultimul appointment si sfarsitul programului
        LocalTime programEnd = doctor.getProgramEnd();
        Appointment lastBusyAppointment = busyAppointments.get(busyAppointments.size() - 1);
        Integer durationLastBusyAppointment = AppointmentType.valueOf(lastBusyAppointment.getAppointmentType().toUpperCase()).getValue();
        LocalTime completedLastBusyAppointmentTime = lastBusyAppointment.getAppointmentTime().plusMinutes(durationLastBusyAppointment);
        if (Duration.between(completedLastBusyAppointmentTime, programEnd).toMinutes() >= duration) {
            TimeInterval freeInterval = TimeInterval.builder()
                    .date(lastBusyAppointment.getAppointmentDate())
                    .start(completedLastBusyAppointmentTime)
                    .end(programEnd)
                    .build();
            freeTimeIntervals.add(freeInterval);
        }

        return freeTimeIntervals;
    }

    @Override
    public List<Appointment> findByDoctorId(Long Id) {
        List<Appointment> appointments = Optional.ofNullable( appointmentRepository.findByDoctorId(Id)).orElse(new ArrayList<>());
        return null;
    }

    @Override
    public List<Appointment> findByPatientId(Long Id) {
        List<Appointment> appointments = Optional.ofNullable( appointmentRepository.findByPatientId(Id)).orElse(new ArrayList<>()) ;
        return null;
    }
}
