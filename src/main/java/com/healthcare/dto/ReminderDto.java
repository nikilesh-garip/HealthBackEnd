package com.healthcare.dto;

import com.healthcare.models.ReminderType;

import java.time.LocalDateTime;

public class ReminderDto {
    private Long id;
    private ReminderType type;
    private String message;
    private LocalDateTime dueAt;
    private boolean sent;

    public ReminderDto(Long id, ReminderType type, String message, LocalDateTime dueAt, boolean sent) {
        this.id = id;
        this.type = type;
        this.message = message;
        this.dueAt = dueAt;
        this.sent = sent;
    }

    public Long getId() {
        return id;
    }

    public ReminderType getType() {
        return type;
    }

    public String getMessage() {
        return message;
    }

    public LocalDateTime getDueAt() {
        return dueAt;
    }

    public boolean isSent() {
        return sent;
    }
}
