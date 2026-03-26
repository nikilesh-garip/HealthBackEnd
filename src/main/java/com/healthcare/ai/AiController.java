package com.healthcare.ai;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.HttpStatus;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/ai")
public class AiController {
    private final AiClient aiClient;

    public AiController(AiClient aiClient) {
        this.aiClient = aiClient;
    }

    @PostMapping("/chatbot")
    public ResponseEntity<Map<String, Object>> chatbot(@RequestBody(required = false) Map<String, Object> payload) {
        try {
            String message = payload == null ? "" : String.valueOf(payload.getOrDefault("message", ""));
            if (message.trim().isEmpty()) {
                throw new IllegalArgumentException("Message is required");
            }
            String reply = aiClient.chatbot(message);
            Map<String, Object> responseBody = new HashMap<>();
            responseBody.put("reply", reply);
            return ResponseEntity.ok(responseBody);
        } catch (IllegalArgumentException ex) {
            Map<String, Object> responseBody = new HashMap<>();
            responseBody.put("message", ex.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(responseBody);
        } catch (IllegalStateException ex) {
            Map<String, Object> responseBody = new HashMap<>();
            responseBody.put("message", ex.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_GATEWAY).body(responseBody);
        } catch (Exception ex) {
            Map<String, Object> responseBody = new HashMap<>();
            responseBody.put("message", "AI service failed: " + ex.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(responseBody);
        }
    }

    @PostMapping("/diet-recommendation")
    public ResponseEntity<Map<String, Object>> dietRecommendation(@RequestBody(required = false) Map<String, Object> payload) {
        try {
            String condition = payload == null ? "" : String.valueOf(payload.getOrDefault("condition", ""));
            if (condition.trim().isEmpty()) {
                throw new IllegalArgumentException("Condition is required");
            }
            String diet = aiClient.dietRecommendation(condition);
            Map<String, Object> responseBody = new HashMap<>();
            responseBody.put("diet", diet);
            return ResponseEntity.ok(responseBody);
        } catch (IllegalArgumentException ex) {
            Map<String, Object> responseBody = new HashMap<>();
            responseBody.put("message", ex.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(responseBody);
        } catch (IllegalStateException ex) {
            Map<String, Object> responseBody = new HashMap<>();
            responseBody.put("message", ex.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_GATEWAY).body(responseBody);
        } catch (Exception ex) {
            Map<String, Object> responseBody = new HashMap<>();
            responseBody.put("message", "AI service failed: " + ex.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(responseBody);
        }
    }

    @PostMapping("/prescription-parse")
    public ResponseEntity<Object> prescriptionParse(@RequestBody(required = false) Map<String, Object> payload) {
        try {
            String text = payload == null ? "" : String.valueOf(payload.getOrDefault("text", ""));
            if (text.trim().isEmpty()) {
                throw new IllegalArgumentException("Text is required");
            }
            Object result = aiClient.parsePrescription(text);
            return ResponseEntity.ok(result);
        } catch (IllegalArgumentException ex) {
            Map<String, Object> responseBody = new HashMap<>();
            responseBody.put("message", ex.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(responseBody);
        } catch (IllegalStateException ex) {
            Map<String, Object> responseBody = new HashMap<>();
            responseBody.put("message", ex.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_GATEWAY).body(responseBody);
        } catch (Exception ex) {
            Map<String, Object> responseBody = new HashMap<>();
            responseBody.put("message", "AI service failed: " + ex.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(responseBody);
        }
    }

}
