package com.integrationhubspot.controller;

import com.integrationhubspot.model.ContactDTO;
import com.integrationhubspot.service.HubSpotService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/contacts")
public class ContactController {
    @Autowired
    private HubSpotService hubSpotService;

    @PostMapping
    public ResponseEntity<String> createContact(@RequestBody ContactDTO contactDTO) {
        try {
            String response = hubSpotService.createContact(contactDTO);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Erro ao criar contato: " + e.getMessage());
        }
    }

    @GetMapping
    public ResponseEntity<String> listContacts() {
        try {
            String response = hubSpotService.listContacts();
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Erro ao listar contatos: " + e.getMessage());
        }
    }
} 