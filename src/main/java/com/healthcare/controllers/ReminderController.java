package com.healthcare.controllers;

import com.healthcare.dto.ReminderDto;
import com.healthcare.repositories.ReminderRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/reminders")
public class ReminderController {
    private final ReminderRepository reminderRepository;

    public ReminderController(ReminderRepository reminderRepository) {
        this.reminderRepository = reminderRepository;
    }

    @GetMapping("/patient/{patientId}")
    public ResponseEntity<List<ReminderDto>> listByPatient(@PathVariable("patientId") Long patientId) {
        List<ReminderDto> reminders = reminderRepository
            .findTop50ByPatientIdOrderByDueAtDesc(patientId)
            .stream()
            .map(reminder -> new ReminderDto(
                reminder.getId(),
                reminder.getType(),
                reminder.getMessage(),
                reminder.getDueAt(),
                reminder.isSent()
            ))
            .toList();
        return ResponseEntity.ok(reminders);
    }
}
