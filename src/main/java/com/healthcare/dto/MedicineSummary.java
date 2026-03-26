package com.healthcare.dto;

public class MedicineSummary {
    private Long id;
    private String medicineName;
    private String dosage;
    private String frequency;
    private String duration;
    private String timeOfDay;
    private String mealTiming;

    public MedicineSummary(Long id, String medicineName, String dosage, String frequency, String duration,
                           String timeOfDay, String mealTiming) {
        this.id = id;
        this.medicineName = medicineName;
        this.dosage = dosage;
        this.frequency = frequency;
        this.duration = duration;
        this.timeOfDay = timeOfDay;
        this.mealTiming = mealTiming;
    }

    public Long getId() {
        return id;
    }

    public String getMedicineName() {
        return medicineName;
    }

    public String getDosage() {
        return dosage;
    }

    public String getFrequency() {
        return frequency;
    }

    public String getDuration() {
        return duration;
    }

    public String getTimeOfDay() {
        return timeOfDay;
    }

    public String getMealTiming() {
        return mealTiming;
    }
}
