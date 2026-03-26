package com.healthcare.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class OrderRequest {
    @NotNull
    private Long patientId;

    private List<OrderItemRequest> items;
}
