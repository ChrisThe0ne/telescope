package dev.bencsik.telescope.controller;

import dev.bencsik.telescope.service.ApiKeyService;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

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

    @PatchMapping("/revoke-key")
    public String revokeApiKey(@RequestParam(name = "apiKeyId") String rawApiKeyId) {
        System.out.println("🔹 Raw Received API Key ID: " + rawApiKeyId);

        try {
            UUID apiKeyId = UUID.fromString(rawApiKeyId);
            System.out.println("Converted API Key ID: " + apiKeyId);

            boolean success = apiKeyService.revokeApiKey(apiKeyId);
            return success ? "API Key revoked successfully." : "API Key not found.";
        } catch (IllegalArgumentException e) {
            System.out.println("Invalid UUID format received: " + rawApiKeyId);
            return "Error: Invalid UUID format.";
        }
    }

    @PatchMapping("/rotate-key")
    public String rotateApiKey(@RequestParam UUID oldApiKeyId) {
        String newApiKey = apiKeyService.rotateApiKey(oldApiKeyId);
        System.out.println("API key received: " + oldApiKeyId);
        return (newApiKey != null) ? "New API Key: " + newApiKey : "Old API Key not found.";
    }
}
