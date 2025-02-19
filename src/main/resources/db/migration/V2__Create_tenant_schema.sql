-- Ensure the function exists for schema creation
CREATE OR REPLACE FUNCTION create_tenant_schema(schema_name TEXT) RETURNS void AS $$
BEGIN
    IF schema_name IS NOT NULL THEN
        EXECUTE format('CREATE SCHEMA IF NOT EXISTS %I', schema_name);
END IF;
END;
$$ LANGUAGE plpgsql;