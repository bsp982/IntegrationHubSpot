package com.integrationhubspot.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/webhook")
public class WebhookController {
    private static final Logger logger = LoggerFactory.getLogger(WebhookController.class);

    @PostMapping("/contact-creation")
    public ResponseEntity<String> receiveContactCreation(@RequestBody String payload) {
        logger.info("Webhook recebido: {}", payload);
        return ResponseEntity.ok("Recebido com sucesso");
    }
} 