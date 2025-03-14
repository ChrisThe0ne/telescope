package dev.bencsik.telescope.service;

import dev.bencsik.telescope.model.TelemetryData;
import dev.bencsik.telescope.repository.TelemetryRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class TelemetryService {

    private final TelemetryRepository telemetryRepository;

    public TelemetryService(TelemetryRepository telemetryRepository) {
        this.telemetryRepository = telemetryRepository;
    }

    public Page<TelemetryData> getFilteredTelemetryData(String metricName, LocalDateTime startDate, LocalDateTime endDate, Pageable pageable) {
        Specification<TelemetryData> spec = Specification.where(null);

        if (metricName != null) {
            spec = spec.and((root, query, cb) -> cb.equal(root.get("metricName"), metricName));
        }
        if (startDate != null) {
            spec = spec.and((root, query, cb) -> cb.greaterThanOrEqualTo(root.get("timestamp"), startDate));
        }
        if (endDate != null) {
            spec = spec.and((root, query, cb) -> cb.lessThanOrEqualTo(root.get("timestamp"), endDate));
        }

        return telemetryRepository.findAll(spec, pageable);
    }

    public TelemetryData saveTelemetry(TelemetryData telemetryData) {
        return telemetryRepository.save(telemetryData);
    }
}