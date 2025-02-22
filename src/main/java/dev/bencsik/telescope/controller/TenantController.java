package dev.bencsik.telescope.controller;

import dev.bencsik.telescope.service.ApiKeyService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/tenants")
public class TenantController {

    private final ApiKeyService apiKeyService;

    public TenantController(ApiKeyService apiKeyService) {
        this.apiKeyService = apiKeyService;
    }

    @PostMapping("/generate-key")
    public String generateApiKey(@RequestParam String tenantId) {
        System.out.println("Entered generateApiKey method for tenant: " + tenantId);

        String newApiKey = apiKeyService.generateAndStoreApiKey(tenantId);

        if (newApiKey == null || newApiKey.isBlank()) {
            System.out.println("Failed to generate API key.");
            return "Error: Could not generate API key.";
        }

        System.out.println("Generated API Key: " + newApiKey);
        return "Generated API Key: " + newApiKey;
    }
}
