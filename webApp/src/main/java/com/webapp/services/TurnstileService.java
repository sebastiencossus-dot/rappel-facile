package com.webapp.services;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Map;

@Service
public class TurnstileService {

    private static final String VERIFY_URL =
            "https://challenges.cloudflare.com/turnstile/v0/siteverify";

    @Value("${turnstile.secret-key}")
    private String secretKey;

    private final RestTemplate restTemplate = new RestTemplate();

    public boolean verify(String token) {

        if (token == null || token.isBlank()) {
            return false;
        }

        Map<String, String> request = Map.of(
                "secret", secretKey,
                "response", token
        );

        try {
            Map response = restTemplate.postForObject(
                    VERIFY_URL,
                    request,
                    Map.class
            );

            return response != null
                    && Boolean.TRUE.equals(response.get("success"));

        } catch (Exception e) {
            return false;
        }
    }
}