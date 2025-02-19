package dev.bencsik.telescope.config;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

@Service
public class TenantSchemaService {

    private final JdbcTemplate jdbcTemplate;

    public TenantSchemaService(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public void createTenantSchema(String schemaName) {
        String sql = "SELECT create_tenant_schema(?)";
        jdbcTemplate.queryForObject(sql, String.class, schemaName);  // Fix applied here
    }
}
