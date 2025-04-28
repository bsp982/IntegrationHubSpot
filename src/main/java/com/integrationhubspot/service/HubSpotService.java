package com.integrationhubspot.service;

import com.integrationhubspot.model.ContactDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import java.util.HashMap;
import java.util.Map;

@Service
public class HubSpotService {
    @Autowired
    private OAuthService oAuthService;

    public String createContact(ContactDTO contactDTO) {
        String accessToken = oAuthService.getAccessToken();
        if (accessToken == null) {
            throw new RuntimeException("Token de acesso não encontrado. Faça o login OAuth primeiro.");
        }

        String url = "https://api.hubapi.com/crm/v3/objects/contacts";
        RestTemplate restTemplate = new RestTemplate();

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setBearerAuth(accessToken);

        Map<String, Object> properties = new HashMap<>();
        properties.put("firstname", contactDTO.getFirstName());
        properties.put("lastname", contactDTO.getLastName());
        properties.put("email", contactDTO.getEmail());

        Map<String, Object> body = new HashMap<>();
        body.put("properties", properties);

        HttpEntity<Map<String, Object>> request = new HttpEntity<>(body, headers);
        ResponseEntity<String> response = restTemplate.postForEntity(url, request, String.class);

        if (response.getStatusCode().is2xxSuccessful()) {
            return response.getBody();
        } else {
            throw new RuntimeException("Erro ao criar contato: " + response.getBody());
        }
    }

    public String listContacts() {
        String accessToken = oAuthService.getAccessToken();
        if (accessToken == null) {
            throw new RuntimeException("Token de acesso não encontrado. Faça o login OAuth primeiro.");
        }

        String url = "https://api.hubapi.com/crm/v3/objects/contacts";
        RestTemplate restTemplate = new RestTemplate();

        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(accessToken);

        HttpEntity<Void> request = new HttpEntity<>(headers);
        ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.GET, request, String.class);

        if (response.getStatusCode().is2xxSuccessful()) {
            return response.getBody();
        } else {
            throw new RuntimeException("Erro ao listar contatos: " + response.getBody());
        }
    }
} 