package com.healthcare.ai;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class AiClient {

    private static final Logger log = LoggerFactory.getLogger(AiClient.class);

    private static final String DEFAULT_CHAT_URL = "https://router.huggingface.co/v1/chat/completions";

    private final RestTemplate restTemplate;
    private final String apiKey;
    private final String chatModel;
    private final String chatUrl;
    private final double temperature;
    
    public AiClient(
            RestTemplate restTemplate,
            @Value("${ai.hf.api-key}") String apiKey,
            @Value("${ai.hf.chat-model}") String chatModel,
            @Value("${ai.hf.chat-url:https://router.huggingface.co/v1/chat/completions}") String chatUrl,
            @Value("${ai.hf.temperature:0}") double temperature
    ) {
        this.restTemplate = restTemplate;
        this.apiKey = apiKey;
        this.chatModel = chatModel;
        this.chatUrl = chatUrl;
        this.temperature = temperature;
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
        String url = normalizeChatUrl(chatUrl);
        log.info("Calling HF chat model: {} at URL: {}", chatModel, url);

        Map<String, Object> body = new HashMap<>();
        body.put("model", chatModel);
        body.put("temperature", temperature);

        List<Map<String, String>> messages = new ArrayList<>();
        messages.add(message("system", systemPrompt));
        messages.add(message("user", userInput));
        body.put("messages", messages);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setBearerAuth(apiKey);

        HttpEntity<Map<String, Object>> request = new HttpEntity<>(body, headers);

        try {
            ResponseEntity<Map> response = restTemplate.postForEntity(url, request, Map.class);
            return extractChatContent(response.getBody());
        } catch (Exception e) {
            throw new IllegalStateException("AI request failed: " + e.getMessage(), e);
        }
    }

    private Map<String, String> message(String role, String content) {
        Map<String, String> msg = new HashMap<>();
        msg.put("role", role);
        msg.put("content", content);
        return msg;
    }

    private String normalizeChatUrl(String configuredUrl) {
        if (configuredUrl == null || configuredUrl.trim().isEmpty()) {
            return DEFAULT_CHAT_URL;
        }
        return configuredUrl.trim();
    }

    private String extractChatContent(Map responseBody) {
        if (responseBody == null) {
            return "No response from AI model.";
        }
        Object choicesObj = responseBody.get("choices");
        if (choicesObj instanceof List<?> choices && !choices.isEmpty()) {
            Object first = choices.get(0);
            if (first instanceof Map<?, ?> choiceMap) {
                Object messageObj = choiceMap.get("message");
                if (messageObj instanceof Map<?, ?> messageMap) {
                    Object content = messageMap.get("content");
                    if (content != null) {
                        return content.toString().trim();
                    }
                }
            }
        }
        return responseBody.toString();
    }
}