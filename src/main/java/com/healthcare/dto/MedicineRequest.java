package com.healthcare.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MedicineRequest {
    private String medicineName;
    private String dosage;
    private String frequency;
    private String duration;
    private String timeOfDay;
    private String mealTiming;
}
