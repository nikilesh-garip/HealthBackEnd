package com.healthcare.repositories;

import com.healthcare.models.Medicine;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MedicineRepository extends JpaRepository<Medicine, Long> {
	@EntityGraph(attributePaths = {
		"prescription",
		"prescription.patient",
		"prescription.patient.user"
	})
	List<Medicine> findAll();
}
