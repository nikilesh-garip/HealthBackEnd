package com.healthcare.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class OrderItemRequest {
    private String medicineName;
    private String dosage;
    private Integer quantity;
}
