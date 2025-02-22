package dev.bencsik.telescope.service;

import dev.bencsik.telescope.model.ApiKey;
import dev.bencsik.telescope.repository.ApiKeyRepository;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;
import java.security.SecureRandom;
import java.util.Base64;

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
        apiKeyRepository.save(apiKey);

        return rawApiKey;
    }

    private String generateRandomApiKey() {
        SecureRandom random = new SecureRandom();
        byte[] keyBytes = new byte[32];
        random.nextBytes(keyBytes);
        return Base64.getUrlEncoder().withoutPadding().encodeToString(keyBytes);
    }
}
