package com.healthcare.repositories;

import com.healthcare.models.Reminder;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;

public interface ReminderRepository extends JpaRepository<Reminder, Long> {
	boolean existsByPatientIdAndTypeAndMessageAndDueAt(Long patientId,
													   com.healthcare.models.ReminderType type,
													   String message,
													   LocalDateTime dueAt);

	List<Reminder> findTop50ByPatientIdOrderByDueAtDesc(Long patientId);
}
