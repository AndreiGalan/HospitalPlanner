package com.example.HospitalPlanner.service;

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
        List<Appointment> appointments = Optional.ofNullable( appointmentRepository.findAll()).orElse(new ArrayList<>());
        return appointments;
    }

    @Override
    public Appointment findById(Long id) {
        Appointment appointment = appointmentRepository.findById(id).orElse(null); // TODO - THROW EXCEPTION
        return appointment;
    }

    @Override
    public TimeInterval create(AppointmentCreationDto appointmentCreationDto) {

        AppointmentType appointmentType = AppointmentType.getType(appointmentCreationDto.getType());

        if (appointmentType == null) {
            // Handle invalid appointment type
            return null;
        }

        Integer durationOfAppointment = appointmentType.getValue();

        DoctorEntity doctor = doctorRepository.findById(appointmentCreationDto.getDoctorId()).orElse(null);

        PatientEntity patient = patientRepository.findById(appointmentCreationDto.getPatientId()).orElse(null);

        if (doctor == null || patient == null) {
            // Handle invalid doctor or patient
            return null;
        }
        // TODO - THROW CUSTOM EXCEPTION

        List<Appointment> appointmentsDoctor = findByDoctorId(appointmentCreationDto.getDoctorId()).stream()
                .filter(appointment -> appointmentCreationDto.getTimeIntervals().stream()
                        .anyMatch(timeInterval -> appointment.getAppointmentDate().equals(timeInterval.getDate())))
                .toList();

        for (TimeInterval timeInterval : appointmentCreationDto.getTimeIntervals()) {
            List<Appointment> busyAppointments = appointmentsDoctor.stream() // TODO - IMPORT FROM REPO BY DATE AND DOCTOR ID (PLSQL)
                    .filter(appointment -> appointment.getAppointmentDate().equals(timeInterval.getDate()))
                    .sorted(Comparator.comparing(Appointment::getAppointmentTime))
                    .toList();

            //TODO - DISCUTIE despre cum sa facem cu intervalele care sunt la aceeasi data dar nu se suprapun

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

        throw new RuntimeException("Doctor is busy at this time");


    }

    private List<TimeInterval> generateFreeIntervals(List<Appointment> busyAppointments,DoctorEntity doctor, Integer duration, LocalDate date, LocalTime start, LocalTime end) {
        List<TimeInterval> freeTimeIntervals = new ArrayList<>();
        System.out.println(busyAppointments);
        // intre inceperea programului si primului appointment
        LocalTime programStart = doctor.getProgramStart();
        LocalTime programEnd = doctor.getProgramEnd();
        if(busyAppointments.isEmpty()) {
            if(start.isAfter(programEnd) || start.equals(programEnd) || end.isBefore(programStart) || end.equals(programStart))
                throw new RuntimeException("Doctor is not available at this time");

            if(start.plusMinutes(duration).isAfter(programEnd)) {
                throw new RuntimeException("Doctor is not available at this time");
            }

            if(start.isBefore(programStart))
                start = programStart;
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
                throw new RuntimeException("Doctor is not available at this time");
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
                throw new RuntimeException("Doctor is not available at this time");
            if(start.plusMinutes(duration).isAfter(programEnd)) {
                throw new RuntimeException("Doctor is not available at this time");
            }
            TimeInterval freeInterval = TimeInterval.builder()
                    .date(lastBusyAppointment.getAppointmentDate())
                    .start(completedLastBusyAppointmentTime)
                    .end(programEnd)
                    .build();
            if(isIntervalIntersecting(freeInterval.getStart(),freeInterval.getEnd(),start,end))
                freeTimeIntervals.add(freeInterval);
        }

        Collections.sort(freeTimeIntervals, Comparator.comparingLong(interval -> Duration.between(interval.getStart(), interval.getEnd()).toMinutes()));

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
    public List<Appointment> findByDoctorId(Long Id) {
        List<Appointment> appointments = Optional.ofNullable( appointmentRepository.findByDoctorId(Id)).orElse(new ArrayList<>());
        return appointments;
    }

    @Override
    public List<Appointment> findByPatientId(Long Id) {
        List<Appointment> appointments = Optional.ofNullable( appointmentRepository.findByPatientId(Id)).orElse(new ArrayList<>()) ;
        return appointments;
    }

    @Override
    public void delete(Long id) {
        appointmentRepository.deleteById(id);
    }
}
