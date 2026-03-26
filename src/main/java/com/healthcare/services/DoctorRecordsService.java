package com.healthcare.services;

import com.healthcare.dto.AppointmentSummary;
import com.healthcare.dto.DoctorPatientHistory;
import com.healthcare.dto.MedicineSummary;
import com.healthcare.dto.PatientSummary;
import com.healthcare.dto.PrescriptionSummary;
import com.healthcare.models.Appointment;
import com.healthcare.models.Patient;
import com.healthcare.models.Prescription;
import com.healthcare.repositories.AppointmentRepository;
import com.healthcare.repositories.PatientRepository;
import com.healthcare.repositories.PrescriptionRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class DoctorRecordsService {
    private final AppointmentRepository appointmentRepository;
    private final PrescriptionRepository prescriptionRepository;
    private final PatientRepository patientRepository;

    public DoctorRecordsService(AppointmentRepository appointmentRepository,
                                PrescriptionRepository prescriptionRepository,
                                PatientRepository patientRepository) {
        this.appointmentRepository = appointmentRepository;
        this.prescriptionRepository = prescriptionRepository;
        this.patientRepository = patientRepository;
    }

    @Transactional(readOnly = true)
    public List<PatientSummary> findPatientsForDoctor(Long doctorId) {
        Map<Long, PatientSummary> result = new LinkedHashMap<>();
        List<Appointment> appointments = appointmentRepository.findByDoctorId(doctorId);
        for (Appointment appointment : appointments) {
            Patient patient = appointment.getPatient();
            result.putIfAbsent(patient.getId(), toPatientSummary(patient));
        }
        List<Prescription> prescriptions = prescriptionRepository.findByDoctorId(doctorId);
        for (Prescription prescription : prescriptions) {
            Patient patient = prescription.getPatient();
            result.putIfAbsent(patient.getId(), toPatientSummary(patient));
        }
        return new ArrayList<>(result.values());
    }

    @Transactional(readOnly = true)
    public DoctorPatientHistory getPatientHistory(Long doctorId, Long patientId) {
        Patient patient = patientRepository.findById(patientId)
            .orElseThrow(() -> new IllegalArgumentException("Patient not found"));

        List<AppointmentSummary> appointments = appointmentRepository
            .findByDoctorIdAndPatientId(doctorId, patientId)
            .stream()
            .map(this::toAppointmentSummary)
            .collect(Collectors.toList());

        List<PrescriptionSummary> prescriptions = prescriptionRepository
            .findByDoctorIdAndPatientId(doctorId, patientId)
            .stream()
            .map(this::toPrescriptionSummary)
            .collect(Collectors.toList());

        return new DoctorPatientHistory(toPatientSummary(patient), appointments, prescriptions);
    }

    private PatientSummary toPatientSummary(Patient patient) {
        return new PatientSummary(
            patient.getId(),
            patient.getUser().getFullName(),
            patient.getUser().getEmail(),
            patient.getUser().getPhone(),
            patient.getAddress()
        );
    }

    private AppointmentSummary toAppointmentSummary(Appointment appointment) {
        return new AppointmentSummary(
            appointment.getId(),
            appointment.getStartTime(),
            appointment.getEndTime(),
            appointment.getReason(),
            appointment.getStatus()
        );
    }

    private PrescriptionSummary toPrescriptionSummary(Prescription prescription) {
        List<MedicineSummary> medicines = prescription.getMedicines()
            .stream()
            .map(medicine -> new MedicineSummary(
                medicine.getId(),
                medicine.getMedicineName(),
                medicine.getDosage(),
                medicine.getFrequency(),
                medicine.getDuration(),
                medicine.getTimeOfDay(),
                medicine.getMealTiming()
            ))
            .collect(Collectors.toList());

        return new PrescriptionSummary(
            prescription.getId(),
            prescription.getInstructions(),
            prescription.getCreatedAt(),
            medicines
        );
    }
}
