package dev.bencsik.telescope.controller;

import dev.bencsik.telescope.dto.TelemetryDataDTO;
import dev.bencsik.telescope.dto.TelemetryDataResponse;
import dev.bencsik.telescope.model.TelemetryData;
import dev.bencsik.telescope.service.TelemetryService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.logging.Logger;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/telemetry")
public class TelemetryController {

    private static final Logger logger = Logger.getLogger(TelemetryController.class.getName());
    private final TelemetryService telemetryService;

    public TelemetryController(TelemetryService telemetryService) {
        this.telemetryService = telemetryService;
    }

    @GetMapping
    public TelemetryDataResponse getTelemetryData(
            @RequestParam(required = false) String metricName,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime startDate,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime endDate,
            Pageable pageable
    ) {
        logger.info("Fetching telemetry data | metricName=" + metricName + ", startDate=" + startDate + ", endDate=" + endDate);

        Page<TelemetryData> telemetryPage = telemetryService.getFilteredTelemetryData(metricName, startDate, endDate, pageable);

        List<TelemetryDataDTO> telemetryList = telemetryPage.getContent().stream()
                .map(data -> new TelemetryDataDTO(
                        data.getId(),
                        data.getTimestamp(),
                        data.getMetricName(),
                        data.getValue()
                ))
                .collect(Collectors.toList());

        return new TelemetryDataResponse(
                telemetryList,
                telemetryPage.getNumber(),
                telemetryPage.getSize(),
                telemetryPage.getTotalElements(),
                telemetryPage.getTotalPages(),
                telemetryPage.isFirst(),
                telemetryPage.isLast()
        );
    }

    @PostMapping
    public TelemetryDataDTO saveTelemetry(@RequestBody TelemetryData telemetryData) {
        logger.info("Saving telemetry data: " + telemetryData);
        TelemetryData savedData = telemetryService.saveTelemetry(telemetryData);
        return new TelemetryDataDTO(
                savedData.getId(),
                savedData.getTimestamp(),
                savedData.getMetricName(),
                savedData.getValue()
        );
    }
}