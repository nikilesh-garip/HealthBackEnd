package com.healthcare.dto;

import com.healthcare.models.Role;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class AuthResponse {
    private Long userId;
    private String email;
    private Role role;
    private Long doctorId;
    private Long patientId;
}
