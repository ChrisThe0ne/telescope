package dev.bencsik.telescope.controller;

import dev.bencsik.telescope.config.TenantSchemaService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/tenants")
public class TenantController {

    private final TenantSchemaService tenantSchemaService;

    public TenantController(TenantSchemaService tenantSchemaService) {
        this.tenantSchemaService = tenantSchemaService;
    }

    @PostMapping("/register")
    public String registerTenant(@RequestParam String tenantName, @RequestParam String schemaName) {
        tenantSchemaService.createTenantSchema(schemaName);
        return "Tenant " + tenantName + " registered with schema: " + schemaName;
    }
}
