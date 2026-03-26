package com.healthcare.controllers;

import com.healthcare.models.Doctor;
import com.healthcare.models.Medicine;
import com.healthcare.models.User;
import com.healthcare.services.AdminService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/admin")
public class AdminController {
    private final AdminService adminService;

    public AdminController(AdminService adminService) {
        this.adminService = adminService;
    }

    @GetMapping("/users")
    public ResponseEntity<List<User>> listUsers() {
        return ResponseEntity.ok(adminService.listUsers());
    }

    @GetMapping("/doctors")
    public ResponseEntity<List<Doctor>> listDoctors() {
        return ResponseEntity.ok(adminService.listDoctors());
    }

    @GetMapping("/medicines")
    public ResponseEntity<List<Medicine>> listMedicines() {
        return ResponseEntity.ok(adminService.listMedicines());
    }
}
