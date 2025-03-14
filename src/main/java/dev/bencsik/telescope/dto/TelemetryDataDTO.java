package dev.bencsik.telescope.dto;

import java.time.LocalDateTime;
import java.util.UUID;

public class TelemetryDataDTO {
    private final UUID id;
    private final LocalDateTime timestamp;
    private final String metricName;
    private final double value;

    public TelemetryDataDTO(UUID id, LocalDateTime timestamp, String metricName, double value) {
        this.id = id;
        this.timestamp = timestamp;
        this.metricName = metricName;
        this.value = value;
    }

    public UUID getId() { return id; }
    public LocalDateTime getTimestamp() { return timestamp; }
    public String getMetricName() { return metricName; }
    public double getValue() { return value; }
}