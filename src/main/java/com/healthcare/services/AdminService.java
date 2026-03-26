package com.healthcare.services;

import com.healthcare.models.Doctor;
import com.healthcare.models.Medicine;
import com.healthcare.models.User;
import com.healthcare.repositories.DoctorRepository;
import com.healthcare.repositories.MedicineRepository;
import com.healthcare.repositories.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AdminService {
    private final UserRepository userRepository;
    private final DoctorRepository doctorRepository;
    private final MedicineRepository medicineRepository;

    public AdminService(UserRepository userRepository,
                        DoctorRepository doctorRepository,
                        MedicineRepository medicineRepository) {
        this.userRepository = userRepository;
        this.doctorRepository = doctorRepository;
        this.medicineRepository = medicineRepository;
    }

    public List<User> listUsers() {
        return userRepository.findAll();
    }

    public List<Doctor> listDoctors() {
        return doctorRepository.findAll();
    }

    public List<Medicine> listMedicines() {
        return medicineRepository.findAll();
    }
}
