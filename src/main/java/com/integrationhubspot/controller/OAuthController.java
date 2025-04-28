package com.integrationhubspot.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.beans.factory.annotation.Autowired;
import com.integrationhubspot.service.OAuthService;

@RestController
@RequestMapping("/oauth")
public class OAuthController {

    @Value("${hubspot.client-id}")
    private String clientId;

    @Value("${hubspot.redirect-uri}")
    private String redirectUri;

    private static final String SCOPE = "crm.objects.contacts.read crm.objects.contacts.write crm.schemas.contacts.read crm.schemas.contacts.write oauth";
    private static final String AUTH_URL = "https://app.hubspot.com/oauth/authorize";

    @Autowired
    private OAuthService oAuthService;

    @GetMapping("/authorize-url")
    public String getAuthorizationUrl() {
        String url = AUTH_URL +
                "?client_id=" + clientId +
                "&redirect_uri=" + redirectUri +
                "&scope=" + SCOPE +
                "&response_type=code";
        return url;
    }

    @GetMapping("/callback")
    public String oauthCallback(@RequestParam("code") String code) {
        try {
            String tokenResponse = oAuthService.exchangeCodeForToken(code);
            return "Autenticação realizada com sucesso! Token: " + tokenResponse;
        } catch (Exception e) {
            return "Erro ao autenticar: " + e.getMessage();
        }
    }
} 