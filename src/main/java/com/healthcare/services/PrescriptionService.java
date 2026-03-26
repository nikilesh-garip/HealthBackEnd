package com.healthcare.services;

import com.healthcare.dto.MedicineRequest;
import com.healthcare.dto.PrescriptionRequest;
import com.healthcare.models.Medicine;
import com.healthcare.models.Prescription;
import com.healthcare.models.Patient;
import com.healthcare.models.Doctor;
import com.healthcare.repositories.PrescriptionRepository;
import com.healthcare.repositories.PatientRepository;
import com.healthcare.repositories.DoctorRepository;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@Service
public class PrescriptionService {
    private final PrescriptionRepository prescriptionRepository;
    private final PatientRepository patientRepository;
    private final DoctorRepository doctorRepository;
    private final ReminderService reminderService;

    public PrescriptionService(PrescriptionRepository prescriptionRepository,
                               PatientRepository patientRepository,
                               DoctorRepository doctorRepository,
                               ReminderService reminderService) {
        this.prescriptionRepository = prescriptionRepository;
        this.patientRepository = patientRepository;
        this.doctorRepository = doctorRepository;
        this.reminderService = reminderService;
    }

    public Prescription create(PrescriptionRequest request) {
        Patient patient = patientRepository.findById(request.getPatientId())
            .orElseThrow(() -> new IllegalArgumentException("Patient not found"));
        Doctor doctor = doctorRepository.findById(request.getDoctorId())
            .orElseThrow(() -> new IllegalArgumentException("Doctor not found"));

        Prescription prescription = new Prescription();
        prescription.setPatient(patient);
        prescription.setDoctor(doctor);
        prescription.setInstructions(request.getInstructions());

        if (request.getMedicines() != null) {
            for (MedicineRequest medicineRequest : request.getMedicines()) {
                Medicine medicine = new Medicine();
                medicine.setPrescription(prescription);
                medicine.setMedicineName(medicineRequest.getMedicineName());
                medicine.setDosage(medicineRequest.getDosage());
                medicine.setFrequency(medicineRequest.getFrequency());
                medicine.setDuration(medicineRequest.getDuration());
                medicine.setTimeOfDay(medicineRequest.getTimeOfDay());
                medicine.setMealTiming(medicineRequest.getMealTiming());
                prescription.getMedicines().add(medicine);
            }
        }

        Prescription saved = prescriptionRepository.save(prescription);
        reminderService.createMedicineReminder(patient, saved);
        return saved;
    }

    public List<Prescription> findByPatient(Long patientId) {
        return prescriptionRepository.findByPatientId(patientId);
    }

    public List<Prescription> findByDoctor(Long doctorId) {
        return prescriptionRepository.findByDoctorId(doctorId);
    }

    public Prescription attachImage(Long prescriptionId, MultipartFile file) {
        Prescription prescription = prescriptionRepository.findById(prescriptionId)
            .orElseThrow(() -> new IllegalArgumentException("Prescription not found"));

        try {
            prescription.setImageData(file.getBytes());
        } catch (IOException ex) {
            throw new IllegalArgumentException("Failed to read image", ex);
        }
        prescription.setImageFileName(file.getOriginalFilename());
        prescription.setImageContentType(file.getContentType());
        return prescriptionRepository.save(prescription);
    }
}
