package dev.bencsik.telescope.controller;

import dev.bencsik.telescope.model.TelemetryData;
import dev.bencsik.telescope.service.TelemetryService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

@RestController
@RequestMapping("/api/telemetry")
public class TelemetryController {

    private final TelemetryService telemetryService;

    public TelemetryController(TelemetryService telemetryService) {
        this.telemetryService = telemetryService;
    }

    @GetMapping
    public Page<TelemetryData> getTelemetryData(
            @RequestParam(required = false) String metricName,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime startDate,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime endDate,
            Pageable pageable
    ) {
        return telemetryService.getFilteredTelemetryData(metricName, startDate, endDate, pageable);
    }

    @PostMapping
    public TelemetryData saveTelemetry(@RequestBody TelemetryData telemetryData) {
        return telemetryService.saveTelemetry(telemetryData);
    }
}