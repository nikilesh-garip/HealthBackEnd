package com.healthcare.controllers;

import com.healthcare.dto.OrderRequest;
import com.healthcare.models.Order;
import com.healthcare.services.OrderService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/orders")
public class OrderController {
    private final OrderService orderService;

    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @PostMapping
    public ResponseEntity<Order> create(@Valid @RequestBody OrderRequest request) {
        return ResponseEntity.ok(orderService.create(request));
    }

    @GetMapping("/patient/{patientId}")
    public ResponseEntity<List<Order>> getByPatient(@PathVariable("patientId") Long patientId) {
        return ResponseEntity.ok(orderService.findByPatient(patientId));
    }

    @GetMapping("/{orderId}")
    public ResponseEntity<Order> getById(@PathVariable("orderId") Long orderId) {
        return ResponseEntity.ok(orderService.findById(orderId));
    }
}
