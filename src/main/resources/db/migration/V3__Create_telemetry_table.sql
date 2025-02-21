CREATE TABLE IF NOT EXISTS telemetry_data (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    timestamp TIMESTAMP DEFAULT NOW(),
    metric_name VARCHAR(255) NOT NULL,
    value DOUBLE PRECISION NOT NULL
);