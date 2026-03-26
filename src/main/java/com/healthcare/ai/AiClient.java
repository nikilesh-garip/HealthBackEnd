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

    log.info("Calling HF chat model: {} at URL: {}", chatModel, chatUrl);

    Map<String, Object> body = new HashMap<>();
    body.put("model", chatModel);

    List<Map<String, String>> messages = new ArrayList<>();
    messages.add(Map.of("role", "system", "content", systemPrompt));
    messages.add(Map.of("role", "user", "content", userInput));

    body.put("messages", messages);

    HttpHeaders headers = new HttpHeaders();
    headers.setContentType(MediaType.APPLICATION_JSON);
    headers.setBearerAuth(apiKey);

    HttpEntity<Map<String, Object>> request = new HttpEntity<>(body, headers);

    try {
        ResponseEntity<Map> response = restTemplate.postForEntity(chatUrl, request, Map.class);

        Map responseBody = response.getBody();

        if (responseBody != null && responseBody.containsKey("choices")) {
            List choices = (List) responseBody.get("choices");
            if (!choices.isEmpty()) {
                Map first = (Map) choices.get(0);
                Map message = (Map) first.get("message");
                return message.get("content").toString();
            }
        }

        return "No response from AI model";

    } catch (Exception e) {
        throw new IllegalStateException("AI request failed: " + e.getMessage(), e);
    }
    }
}
