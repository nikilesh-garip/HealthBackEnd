package com.healthcare.services;

import com.healthcare.dto.OrderRequest;
import com.healthcare.models.Order;
import com.healthcare.models.OrderItem;
import com.healthcare.models.Patient;
import com.healthcare.repositories.OrderRepository;
import com.healthcare.repositories.PatientRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrderService {
    private final OrderRepository orderRepository;
    private final PatientRepository patientRepository;
    private final ReminderService reminderService;

    public OrderService(OrderRepository orderRepository,
                        PatientRepository patientRepository,
                        ReminderService reminderService) {
        this.orderRepository = orderRepository;
        this.patientRepository = patientRepository;
        this.reminderService = reminderService;
    }

    public Order create(OrderRequest request) {
        Patient patient = patientRepository.findById(request.getPatientId())
            .orElseThrow(() -> new IllegalArgumentException("Patient not found"));

        Order order = new Order();
        order.setPatient(patient);
        order.setDeliveryAddress(patient.getAddress());
        if (request.getItems() != null) {
            for (var itemRequest : request.getItems()) {
                if (itemRequest.getMedicineName() == null || itemRequest.getMedicineName().isBlank()) {
                    continue;
                }
                OrderItem item = new OrderItem();
                item.setOrder(order);
                item.setMedicineName(itemRequest.getMedicineName());
                item.setDosage(itemRequest.getDosage());
                item.setQuantity(itemRequest.getQuantity());
                order.getItems().add(item);
            }
        }
        Order saved = orderRepository.save(order);

        reminderService.createOrderConfirmation(patient, saved);
        return saved;
    }

    public List<Order> findByPatient(Long patientId) {
        return orderRepository.findByPatientId(patientId);
    }

    public Order findById(Long orderId) {
        return orderRepository.findById(orderId)
            .orElseThrow(() -> new IllegalArgumentException("Order not found"));
    }
}
