package com.healthcare.services;

import com.healthcare.dto.AuthResponse;
import com.healthcare.dto.LoginRequest;
import com.healthcare.dto.RegisterRequest;
import com.healthcare.models.Doctor;
import com.healthcare.models.Patient;
import com.healthcare.models.Role;
import com.healthcare.models.User;
import com.healthcare.repositories.DoctorRepository;
import com.healthcare.repositories.PatientRepository;
import com.healthcare.repositories.UserRepository;
import org.springframework.stereotype.Service;


@Service
public class AuthService {
    private final UserRepository userRepository;
    private final PatientRepository patientRepository;
    private final DoctorRepository doctorRepository;
    public AuthService(UserRepository userRepository,
                       PatientRepository patientRepository,
                       DoctorRepository doctorRepository) {
        this.userRepository = userRepository;
        this.patientRepository = patientRepository;
        this.doctorRepository = doctorRepository;
    }

    public AuthResponse register(RegisterRequest request) {
        if (userRepository.findByEmail(request.getEmail()).isPresent()) {
            throw new IllegalArgumentException("Email already registered");
        }
        User user = new User();
        user.setEmail(request.getEmail());
        user.setPassword(request.getPassword());
        user.setRole(request.getRole());
        user.setPhone(request.getPhone());
        user.setFullName(request.getFullName());
        userRepository.save(user);

        Long doctorId = null;
        Long patientId = null;
        if (request.getRole() == Role.PATIENT) {
            Patient patient = new Patient();
            patient.setUser(user);
            patientRepository.save(patient);
            patientId = patient.getId();
        } else if (request.getRole() == Role.DOCTOR) {
            Doctor doctor = new Doctor();
            doctor.setUser(user);
            doctorRepository.save(doctor);
            doctorId = doctor.getId();
        }

        return new AuthResponse(user.getId(), user.getEmail(), user.getRole(), doctorId, patientId);
    }

    public AuthResponse login(LoginRequest request) {
        User user = userRepository.findByEmail(request.getEmail())
            .orElseThrow(() -> new IllegalArgumentException("Invalid credentials"));
        if (!request.getPassword().equals(user.getPassword())) {
            throw new IllegalArgumentException("Invalid credentials");
        }
        Long doctorId = null;
        Long patientId = null;
        if (user.getRole() == Role.DOCTOR) {
            doctorId = doctorRepository.findByUserId(user.getId())
                .map(Doctor::getId)
                .orElse(null);
        } else if (user.getRole() == Role.PATIENT) {
            patientId = patientRepository.findByUserId(user.getId())
                .map(Patient::getId)
                .orElse(null);
        }
        return new AuthResponse(user.getId(), user.getEmail(), user.getRole(), doctorId, patientId);
    }
}
