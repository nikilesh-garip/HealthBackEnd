package com.healthcare.services;

import com.healthcare.models.*;
import com.healthcare.notification.TwilioNotificationService;
import com.healthcare.repositories.ReminderRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class ReminderService {
    private static final Logger logger = LoggerFactory.getLogger(ReminderService.class);
    private final ReminderRepository reminderRepository;
    private final TwilioNotificationService twilioNotificationService;

    public ReminderService(ReminderRepository reminderRepository,
                           TwilioNotificationService twilioNotificationService) {
        this.reminderRepository = reminderRepository;
        this.twilioNotificationService = twilioNotificationService;
    }

    public Reminder createAppointmentConfirmation(Patient patient, Appointment appointment) {
        String message = "Appointment confirmed for " + appointment.getStartTime();
        return createAndSend(patient, ReminderType.APPOINTMENT_CONFIRMATION, message, LocalDateTime.now());
    }

    public Reminder createMedicineReminder(Patient patient, Prescription prescription) {
        String message = "New prescription added. Please follow your medicine schedule.";
        return createAndSend(patient, ReminderType.MEDICINE_REMINDER, message, LocalDateTime.now());
    }

    public Reminder createOrderConfirmation(Patient patient, Order order) {
        String message = "Order confirmed. Order ID: " + order.getId();
        return createAndSend(patient, ReminderType.ORDER_CONFIRMATION, message, LocalDateTime.now());
    }

    public Reminder createRefillAlert(Patient patient, String message, LocalDateTime dueAt) {
        return createAndSend(patient, ReminderType.REFILL_ALERT, message, dueAt);
    }

    public void sendScheduledMedicineReminder(Patient patient, String message, LocalDateTime dueAt) {
        createAndSend(patient, ReminderType.MEDICINE_REMINDER, message, dueAt);
    }

    public String buildMedicineReminderMessage(Patient patient, Medicine medicine, java.time.LocalTime time) {
        String mealTiming = medicine.getMealTiming() == null ? "" : medicine.getMealTiming();
        String dosage = medicine.getDosage() == null ? "" : medicine.getDosage();
        String frequency = medicine.getFrequency() == null ? "" : medicine.getFrequency();
        return String.format(
            "Reminder: take %s %s at %s %s %s",
            medicine.getMedicineName(),
            dosage,
            time,
            frequency,
            mealTiming
        ).replaceAll("\\s+", " ").trim();
    }

    private Reminder createAndSend(Patient patient, ReminderType type, String message, LocalDateTime dueAt) {
        Reminder reminder = new Reminder();
        reminder.setPatient(patient);
        reminder.setType(type);
        reminder.setMessage(message);
        reminder.setDueAt(dueAt);
        reminder.setSent(false);
        Reminder saved = reminderRepository.save(reminder);

        if (patient.getUser() == null || patient.getUser().getPhone() == null) {
            throw new IllegalArgumentException("Patient phone number is missing");
        }
        twilioNotificationService.sendSms(patient.getUser().getPhone(), message);
        saved.setSent(true);
        reminderRepository.save(saved);
        return saved;
    }
}
