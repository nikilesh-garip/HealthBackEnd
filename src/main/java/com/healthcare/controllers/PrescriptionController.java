package com.healthcare.controllers;

import com.healthcare.dto.PrescriptionRequest;
import com.healthcare.models.Prescription;
import com.healthcare.services.PrescriptionService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/prescriptions")
public class PrescriptionController {
    private final PrescriptionService prescriptionService;

    public PrescriptionController(PrescriptionService prescriptionService) {
        this.prescriptionService = prescriptionService;
    }

    @PostMapping
    public ResponseEntity<Prescription> create(@Valid @RequestBody PrescriptionRequest request) {
        return ResponseEntity.ok(prescriptionService.create(request));
    }

    @GetMapping("/patient/{patientId}")
    public ResponseEntity<List<Prescription>> getByPatient(@PathVariable("patientId") Long patientId) {
        return ResponseEntity.ok(prescriptionService.findByPatient(patientId));
    }

    @GetMapping("/doctor/{doctorId}")
    public ResponseEntity<List<Prescription>> getByDoctor(@PathVariable("doctorId") Long doctorId) {
        return ResponseEntity.ok(prescriptionService.findByDoctor(doctorId));
    }

    @PostMapping("/{prescriptionId}/image")
    public ResponseEntity<Prescription> uploadImage(@PathVariable("prescriptionId") Long prescriptionId,
                                                    @RequestParam("file") MultipartFile file) {
        return ResponseEntity.ok(prescriptionService.attachImage(prescriptionId, file));
    }
}
