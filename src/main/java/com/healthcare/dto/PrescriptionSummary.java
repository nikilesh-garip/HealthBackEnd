package com.healthcare.dto;

import java.time.LocalDateTime;
import java.util.List;

public class PrescriptionSummary {
    private Long id;
    private String instructions;
    private LocalDateTime createdAt;
    private List<MedicineSummary> medicines;

    public PrescriptionSummary(Long id, String instructions, LocalDateTime createdAt, List<MedicineSummary> medicines) {
        this.id = id;
        this.instructions = instructions;
        this.createdAt = createdAt;
        this.medicines = medicines;
    }

    public Long getId() {
        return id;
    }

    public String getInstructions() {
        return instructions;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public List<MedicineSummary> getMedicines() {
        return medicines;
    }
}
