package com.healthcare.repositories;

import com.healthcare.models.Prescription;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PrescriptionRepository extends JpaRepository<Prescription, Long> {
    @EntityGraph(attributePaths = "medicines")
    List<Prescription> findByPatientId(Long patientId);

    @EntityGraph(attributePaths = "medicines")
    List<Prescription> findByDoctorId(Long doctorId);

    @EntityGraph(attributePaths = "medicines")
    List<Prescription> findByDoctorIdAndPatientId(Long doctorId, Long patientId);
}
