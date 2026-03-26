package com.healthcare.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "medicines")
public class Medicine {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(optional = false)
    @JoinColumn(name = "prescription_id")
    @JsonIgnore
    private Prescription prescription;

    private String medicineName;

    private String dosage;

    private String frequency;

    private String duration;

    private String timeOfDay;

    private String mealTiming;
}
