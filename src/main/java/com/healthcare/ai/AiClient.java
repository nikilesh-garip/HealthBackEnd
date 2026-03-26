package com.healthcare.ai;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.http.*;
import org.springframework.web.client.RestTemplate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;

@Component
public class AiClient {

    private static final Logger log = LoggerFactory.getLogger(AiClient.class);

    private final RestTemplate restTemplate;
    private final String apiKey;
    private final String chatModel;
    private final String chatUrl;

    public AiClient(
            RestTemplate restTemplate,
            @Value("${ai.hf.api-key}") String apiKey,
            @Value("${ai.hf.chat-model}") String chatModel,
            @Value("${ai.hf.chat-url}") String chatUrl
    ) {
        this.restTemplate = restTemplate;
        this.apiKey = apiKey;
        this.chatModel = chatModel;
        this.chatUrl = chatUrl;
    }

    public Object parsePrescription(String text) {
        String prompt = "Extract medicines, dosage, frequency, duration, time_of_day, and meal_timing from this prescription. Return JSON.";
        return chatCompletion(prompt, text);
    }

    public String chatbot(String message) {
        String prompt = "You are a helpful medical assistant. Give clear, safe, and simple advice.";
        return chatCompletion(prompt, message);
    }

    public String dietRecommendation(String condition) {
        String prompt = "Provide diet suggestions for the given condition.";
        return chatCompletion(prompt, condition);
    }

    private String chatCompletion(String systemPrompt, String userInput) {
        if (apiKey == null || apiKey.trim().isEmpty()) {
            throw new IllegalStateException("HF_API_KEY is not set");
        }

        log.info("Calling HF inference model: {} at URL: {}", chatModel, chatUrl);

        // 🔥 Correct body for inference API
        Map<String, Object> body = new HashMap<>();
        body.put("inputs", systemPrompt + "\nUser: " + userInput);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setBearerAuth(apiKey);

        HttpEntity<Map<String, Object>> request = new HttpEntity<>(body, headers);

        try {
            ResponseEntity<List> response = restTemplate.postForEntity(chatUrl, request, List.class);

            if (response.getBody() != null && !response.getBody().isEmpty()) {
                Map first = (Map) response.getBody().get(0);
                Object text = first.get("generated_text");
                if (text != null) {
                    return text.toString();
                }
            }

            return "No response from AI model";

        } catch (Exception e) {
            throw new IllegalStateException("AI request failed: " + e.getMessage(), e);
        }
    }
}
