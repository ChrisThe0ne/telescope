package dev.bencsik.telescope.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class TestController {

    private static final Logger logger = LoggerFactory.getLogger(TestController.class);

    @GetMapping("/test")
    public String testEndpoint(@RequestHeader(value = "X-Tenant-ID", required = false) String tenantId) {
        logger.info("Received request on /api/test with Tenant-ID: {}", tenantId);
        return "Test successful. Tenant: " + (tenantId != null ? tenantId : "No tenant header provided");
    }
}
