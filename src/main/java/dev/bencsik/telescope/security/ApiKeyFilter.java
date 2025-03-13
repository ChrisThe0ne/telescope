package dev.bencsik.telescope.security;

import dev.bencsik.telescope.model.ApiKey;
import dev.bencsik.telescope.repository.ApiKeyRepository;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;
import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class ApiKeyFilter implements Filter {

    private final ApiKeyRepository apiKeyRepository;
    private final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    public ApiKeyFilter(ApiKeyRepository apiKeyRepository) {
        this.apiKeyRepository = apiKeyRepository;
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {

        HttpServletRequest httpRequest = (HttpServletRequest) request;
        String requestUri = httpRequest.getRequestURI();

        // Skip API key validation for tenant management & system endpoints
        if (requestUri.equals("/api/tenants/generate-key") ||
                requestUri.equals("/api/tenants/revoke-key") ||
                requestUri.equals("/api/tenants/rotate-key") ||
                requestUri.startsWith("/actuator")) {

            System.out.println("🔹 Skipping API key check for: " + requestUri);
            chain.doFilter(request, response);
            return;
        }

        String apiKey = httpRequest.getHeader("X-API-KEY");
        System.out.println("Incoming request to: " + requestUri);
        System.out.println("Received API Key: " + apiKey);

        if (apiKey == null || apiKey.isBlank()) {
            System.out.println("Missing API Key, rejecting request.");
            ((HttpServletResponse) response).sendError(HttpServletResponse.SC_UNAUTHORIZED, "Missing API Key");
            return;
        }

        boolean validKey = false;
        for (ApiKey storedKey : apiKeyRepository.findAll()) {
            //System.out.println("Checking API Key: " + storedKey.getId() + " (Revoked: " + storedKey.isRevoked() + ")");
            if (!storedKey.isRevoked() && passwordEncoder.matches(apiKey, storedKey.getApiKeyHash())) {
                validKey = true;
                break;
            }
        }

        if (!validKey) {
            System.out.println("Invalid or revoked API Key, rejecting request.");
            ((HttpServletResponse) response).sendError(HttpServletResponse.SC_UNAUTHORIZED, "Invalid or revoked API Key");
            return;
        }

        System.out.println("API Key valid, processing request.");
        chain.doFilter(request, response);
    }
}
