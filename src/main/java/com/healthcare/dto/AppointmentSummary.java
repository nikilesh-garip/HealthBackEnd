package com.healthcare.dto;

import com.healthcare.models.AppointmentStatus;

import java.time.LocalDateTime;

public class AppointmentSummary {
    private Long id;
    private LocalDateTime startTime;
    private LocalDateTime endTime;
    private String reason;
    private AppointmentStatus status;

    public AppointmentSummary(Long id, LocalDateTime startTime, LocalDateTime endTime, String reason, AppointmentStatus status) {
        this.id = id;
        this.startTime = startTime;
        this.endTime = endTime;
        this.reason = reason;
        this.status = status;
    }

    public Long getId() {
        return id;
    }

    public LocalDateTime getStartTime() {
        return startTime;
    }

    public LocalDateTime getEndTime() {
        return endTime;
    }

    public String getReason() {
        return reason;
    }

    public AppointmentStatus getStatus() {
        return status;
    }
}
