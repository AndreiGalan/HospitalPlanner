package com.example.HospitalPlanner.service;

import com.example.HospitalPlanner.advice.exceptions.*;
import com.example.HospitalPlanner.dto.AppointmentCreationDto;
import com.example.HospitalPlanner.entity.Appointment;
import com.example.HospitalPlanner.entity.DoctorEntity;
import com.example.HospitalPlanner.entity.PatientEntity;
import com.example.HospitalPlanner.repo.AppointmentRepository;
import com.example.HospitalPlanner.repo.DoctorRepository;
import com.example.HospitalPlanner.repo.PatientRepository;
import com.example.HospitalPlanner.util.AppointmentType;
import com.example.HospitalPlanner.util.TimeInterval;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.*;

@Service
public class AppointmentServiceImpl implements AppointmentService {

    private final AppointmentRepository appointmentRepository;
    private final DoctorRepository doctorRepository;
    private final PatientRepository patientRepository;

    @Autowired
    public AppointmentServiceImpl(AppointmentRepository appointmentRepository, DoctorRepository doctorRepository, PatientRepository patientRepository) {
        this.appointmentRepository = appointmentRepository;
        this.doctorRepository = doctorRepository;
        this.patientRepository = patientRepository;
    }


    @Override
    public List<Appointment> findAll() {
        return Optional.ofNullable( appointmentRepository.findAll()).orElse(new ArrayList<>());
    }

    @Override
    public Appointment findById(Long id) {
        return appointmentRepository.findById(id).orElseThrow(()->new AppointmentNotFoundException(id));
    }

    @Override
    public TimeInterval create(AppointmentCreationDto appointmentCreationDto) {

        AppointmentType appointmentType = AppointmentType.getType(appointmentCreationDto.getType());

        Integer durationOfAppointment = appointmentType.getValue();

        DoctorEntity doctor = doctorRepository.findById(appointmentCreationDto.getDoctorId()).orElseThrow(()->new DoctorNotFoundException(appointmentCreationDto.getDoctorId()));

        PatientEntity patient = patientRepository.findById(appointmentCreationDto.getPatientId()).orElseThrow(()->new PatientNotFoundException(appointmentCreationDto.getPatientId()));


        for (TimeInterval timeInterval : appointmentCreationDto.getTimeIntervals()) {
            List<Appointment> busyAppointments = appointmentRepository.findByDateAndDoctorId(timeInterval.getDate(),doctor.getId());

            List<TimeInterval> freeTimeIntervals = generateFreeIntervals(busyAppointments,doctor,durationOfAppointment, timeInterval.getDate(), timeInterval.getStart(), timeInterval.getEnd());

            if(!freeTimeIntervals.isEmpty()) {
                TimeInterval freeInterval = freeTimeIntervals.get(0);

                if(freeInterval.getStart().isBefore(timeInterval.getStart()))
                    freeInterval.setStart(timeInterval.getStart());

                freeInterval.setEnd(freeInterval.getStart().plusMinutes(durationOfAppointment));

                System.out.println(freeInterval);
                Appointment appointment = Appointment.builder()
                        .appointmentDate(freeInterval.getDate())
                        .appointmentTime(freeInterval.getStart())
                        .doctorEntity(doctor)
                        .patientEntity(patient)
                        .appointmentType(appointmentCreationDto.getType())
                        .build();
                appointmentRepository.save(appointment);

                return freeInterval;
            }
        }

        throw new DoctorIsBusyException("Doctor is busy at this time");

    }

    private List<TimeInterval> generateFreeIntervals(List<Appointment> busyAppointments,DoctorEntity doctor, Integer duration, LocalDate date, LocalTime start, LocalTime end) {
        List<TimeInterval> freeTimeIntervals = new ArrayList<>();
        System.out.println(busyAppointments);
        // intre inceperea programului si primului appointment
        LocalTime programStart = doctor.getProgramStart();
        LocalTime programEnd = doctor.getProgramEnd();
        if(busyAppointments.isEmpty()) {
            if(start.isAfter(programEnd) || start.equals(programEnd) || end.isBefore(programStart) || end.equals(programStart))
                throw new DoctorIsBusyException("Doctor is not available at this time:" + start + " " + end + " " + date);

            if(start.plusMinutes(duration).isAfter(programEnd)) {
                throw new DoctorIsBusyException("Doctor is not available at this time:" + start + " " + end + " " + date);
            }

            if(start.isBefore(programStart)) start = programStart;

            TimeInterval freeInterval = TimeInterval.builder()
                    .date(date)
                    .start(start)
                    .end(programStart.plusMinutes(duration))
                    .build();
            freeTimeIntervals.add(freeInterval);

            return freeTimeIntervals;
        }

        LocalTime firstBusyAppointmentTime = busyAppointments.get(0).getAppointmentTime();
        if (Duration.between(programStart, firstBusyAppointmentTime).toMinutes() >= duration) {

            if(start.plusMinutes(duration).isAfter(programEnd)) {
                throw new DoctorIsBusyException("Doctor is not available at this time");
            }

            TimeInterval freeInterval = TimeInterval.builder()
                    .date(busyAppointments.get(0).getAppointmentDate())
                    .start(programStart)
                    .end(firstBusyAppointmentTime)
                    .build();
            if(isIntervalIntersecting(freeInterval.getStart(),freeInterval.getEnd(),start,end))
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
                if(isIntervalIntersecting(freeInterval.getStart(),freeInterval.getEnd(),start,end))
                    freeTimeIntervals.add(freeInterval);
            }
        }

        // intre ultimul appointment si sfarsitul programului
        Appointment lastBusyAppointment = busyAppointments.get(busyAppointments.size() - 1);
        Integer durationLastBusyAppointment = AppointmentType.valueOf(lastBusyAppointment.getAppointmentType().toUpperCase()).getValue();
        LocalTime completedLastBusyAppointmentTime = lastBusyAppointment.getAppointmentTime().plusMinutes(durationLastBusyAppointment);
        if (Duration.between(completedLastBusyAppointmentTime, programEnd).toMinutes() >= duration) {
            if(start.isAfter(programEnd) || start.equals(programEnd) || end.isBefore(programStart) || end.equals(programStart))
                throw new DoctorIsBusyException("Doctor is not available at this time");
            if(start.plusMinutes(duration).isAfter(programEnd)) {
                throw new DoctorIsBusyException("Doctor is not available at this time");
            }
            TimeInterval freeInterval = TimeInterval.builder()
                    .date(lastBusyAppointment.getAppointmentDate())
                    .start(completedLastBusyAppointmentTime)
                    .end(programEnd)
                    .build();
            if(isIntervalIntersecting(freeInterval.getStart(),freeInterval.getEnd(),start,end))
                freeTimeIntervals.add(freeInterval);
        }

        freeTimeIntervals.sort(Comparator.comparingLong(interval -> Duration.between(interval.getStart(), interval.getEnd()).toMinutes()));

        System.out.println(freeTimeIntervals);

        return freeTimeIntervals;
    }

    public static boolean isIntervalIntersecting(LocalTime x1, LocalTime x2, LocalTime y1, LocalTime y2) {
        return (x1.isAfter(y1) && x1.isBefore(y2)) ||
                (x2.isAfter(y1) && x2.isBefore(y2)) ||
                (y1.isAfter(x1) && y1.isBefore(x2)) ||
                (y2.isAfter(x1) && y2.isBefore(x2)) ||
                x1.equals(y1) || x2.equals(y2);
    }

    @Override
    public List<Appointment> findByDoctorId(Long id) {
        doctorRepository.findById(id).orElseThrow(()-> new DoctorNotFoundException(id));
        List<Appointment> appointments = Optional.ofNullable( appointmentRepository.findByDoctorId(id)).orElse(new ArrayList<>());
        System.out.println(appointments);
        return appointments;
    }

    @Override
    public List<Appointment> findByPatientId(Long id) {
        patientRepository.findById(id).orElseThrow(()-> new PatientNotFoundException(id));
        return Optional.ofNullable( appointmentRepository.findByPatientId(id)).orElse(new ArrayList<>());
    }
    @Override
    public List<Appointment> getByDateIdAndDate(Long doctorId, LocalDate date) {
        doctorRepository.findById(doctorId).orElseThrow(()-> new DoctorNotFoundException(doctorId));
        return Optional.ofNullable( appointmentRepository.findByDateAndDoctorId(date,doctorId)).orElse(new ArrayList<>());
    }

    @Override
    public void delete(Long id) {
        appointmentRepository.findById(id).orElseThrow(() -> new AppointmentNotFoundException(id));
        appointmentRepository.deleteById(id);
    }
}
