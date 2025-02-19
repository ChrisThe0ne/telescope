-- Table to store registered tenants
CREATE TABLE IF NOT EXISTS master_tenant (
                                             id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    tenant_name VARCHAR(255) UNIQUE NOT NULL,
    schema_name VARCHAR(255) UNIQUE NOT NULL,
    created_at TIMESTAMP DEFAULT NOW()
    );

-- Insert a sample tenant (for testing)
INSERT INTO master_tenant (tenant_name, schema_name)
VALUES ('Test Company', 'tenant_test')
    ON CONFLICT (tenant_name) DO NOTHING;