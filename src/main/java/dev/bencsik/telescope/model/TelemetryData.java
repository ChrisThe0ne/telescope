package dev.bencsik.telescope.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "telemetry_data")
public class TelemetryData {

    @Id
    @GeneratedValue
    private UUID id;

    private LocalDateTime timestamp;

    @Column(name = "metric_name", nullable = false)
    @JsonProperty("metricName")
    private String metricName;

    @Column(nullable = false)
    private double value;

    @PrePersist
    protected void onCreate() {
        this.timestamp = LocalDateTime.now();
    }

    // Debugging
    @Override
    public String toString() {
        return "TelemetryData{" +
                "id=" + id +
                ", timestamp=" + timestamp +
                ", metricName='" + metricName + '\'' +
                ", value=" + value +
                '}';
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
    }

    public String getMetricName() {
        return metricName;
    }

    public void setMetricName(String metricName) {
        this.metricName = metricName;
    }

    public double getValue() {
        return value;
    }

    public void setValue(double value) {
        this.value = value;
    }
}
