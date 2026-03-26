package com.healthcare.services;

import com.healthcare.models.Medicine;
import com.healthcare.models.Patient;
import com.healthcare.models.ReminderType;
import com.healthcare.repositories.MedicineRepository;
import com.healthcare.repositories.ReminderRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ReminderScheduler {
    private static final Logger logger = LoggerFactory.getLogger(ReminderScheduler.class);
    private static final DateTimeFormatter TIME_FORMAT = DateTimeFormatter.ofPattern("HH:mm");

    private final MedicineRepository medicineRepository;
    private final ReminderRepository reminderRepository;
    private final ReminderService reminderService;

    public ReminderScheduler(MedicineRepository medicineRepository,
                             ReminderRepository reminderRepository,
                             ReminderService reminderService) {
        this.medicineRepository = medicineRepository;
        this.reminderRepository = reminderRepository;
        this.reminderService = reminderService;
    }

    @Scheduled(cron = "0 * * * * *")
    public void sendMedicineReminders() {
        LocalDateTime now = LocalDateTime.now().withSecond(0).withNano(0);
        LocalTime currentTime = now.toLocalTime();
        List<Medicine> medicines = medicineRepository.findAll();

        for (Medicine medicine : medicines) {
            String times = medicine.getTimeOfDay();
            if (times == null || times.isBlank()) {
                continue;
            }
            List<LocalTime> scheduledTimes = parseTimes(times);
            if (!scheduledTimes.contains(currentTime)) {
                continue;
            }
            if (medicine.getPrescription() == null || medicine.getPrescription().getPatient() == null) {
                continue;
            }
            Patient patient = medicine.getPrescription().getPatient();
            String message = reminderService.buildMedicineReminderMessage(patient, medicine, now.toLocalTime());
            boolean alreadySent = reminderRepository.existsByPatientIdAndTypeAndMessageAndDueAt(
                patient.getId(),
                ReminderType.MEDICINE_REMINDER,
                message,
                now
            );
            if (alreadySent) {
                continue;
            }
            try {
                reminderService.sendScheduledMedicineReminder(patient, message, now);
            } catch (Exception ex) {
                logger.warn("Failed to send scheduled reminder for patient {}: {}",
                    patient.getId(), ex.getMessage());
            }
        }
    }

    private List<LocalTime> parseTimes(String times) {
        return Arrays.stream(times.split(","))
            .map(String::trim)
            .filter(value -> !value.isEmpty())
            .map(value -> {
                try {
                    return LocalTime.parse(value, TIME_FORMAT);
                } catch (DateTimeParseException ex) {
                    return null;
                }
            })
            .filter(value -> value != null)
            .collect(Collectors.toList());
    }
}
