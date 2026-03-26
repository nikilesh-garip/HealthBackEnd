package com.healthcare.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class PrescriptionRequest {
    @NotNull
    private Long patientId;

    @NotNull
    private Long doctorId;

    private String instructions;

    private List<MedicineRequest> medicines;
}
