package dev.bencsik.telescope.service;

import dev.bencsik.telescope.model.ApiKey;
import dev.bencsik.telescope.repository.ApiKeyRepository;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.security.SecureRandom;
import java.util.Base64;
import java.util.Optional;
import java.util.UUID;

@Service
public class ApiKeyService {

    private final ApiKeyRepository apiKeyRepository;
    private final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    public ApiKeyService(ApiKeyRepository apiKeyRepository) {
        this.apiKeyRepository = apiKeyRepository;
    }

    public String generateAndStoreApiKey(String tenantId) {
        String rawApiKey = generateRandomApiKey();
        String hashedApiKey = passwordEncoder.encode(rawApiKey);

        ApiKey apiKey = new ApiKey();
        apiKey.setTenantId(tenantId);
        apiKey.setApiKeyHash(hashedApiKey);
        apiKey.setRevoked(false);

        apiKeyRepository.save(apiKey);
        System.out.println("New API Key generated for tenant: " + tenantId);
        return rawApiKey;
    }

    private String generateRandomApiKey() {
        SecureRandom random = new SecureRandom();
        byte[] keyBytes = new byte[32];
        random.nextBytes(keyBytes);
        return Base64.getUrlEncoder().withoutPadding().encodeToString(keyBytes);
    }

    public boolean revokeApiKey(UUID apiKeyId) {
        Optional<ApiKey> apiKeyOptional = apiKeyRepository.findById(apiKeyId);
        if (apiKeyOptional.isPresent()) {
            ApiKey apiKey = apiKeyOptional.get();
            apiKey.setRevoked(true);
            apiKeyRepository.save(apiKey);
            System.out.println("API Key revoked: " + apiKeyId);
            return true;
        }
        System.out.println("API Key not found: " + apiKeyId);
        return false;
    }

    public String rotateApiKey(UUID oldApiKeyId) {
        Optional<ApiKey> oldApiKeyOptional = apiKeyRepository.findById(oldApiKeyId);
        if (oldApiKeyOptional.isPresent()) {
            ApiKey oldApiKey = oldApiKeyOptional.get();

            oldApiKey.setRevoked(true);
            apiKeyRepository.save(oldApiKey);
            System.out.println("Revoked old API Key: " + oldApiKeyId);

            return generateAndStoreApiKey(oldApiKey.getTenantId());
        }
        System.out.println("Old API Key not found: " + oldApiKeyId);
        return null;
    }

}
