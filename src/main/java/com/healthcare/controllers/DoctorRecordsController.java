package com.healthcare.controllers;

import com.healthcare.dto.DoctorPatientHistory;
import com.healthcare.dto.PatientSummary;
import com.healthcare.services.DoctorRecordsService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/doctors")
public class DoctorRecordsController {
    private final DoctorRecordsService doctorRecordsService;

    public DoctorRecordsController(DoctorRecordsService doctorRecordsService) {
        this.doctorRecordsService = doctorRecordsService;
    }

    @GetMapping("/{doctorId}/patients")
    public ResponseEntity<List<PatientSummary>> listPatients(@PathVariable("doctorId") Long doctorId) {
        return ResponseEntity.ok(doctorRecordsService.findPatientsForDoctor(doctorId));
    }

    @GetMapping("/{doctorId}/patients/{patientId}/history")
    public ResponseEntity<DoctorPatientHistory> getHistory(@PathVariable("doctorId") Long doctorId,
                                                           @PathVariable("patientId") Long patientId) {
        return ResponseEntity.ok(doctorRecordsService.getPatientHistory(doctorId, patientId));
    }
}
