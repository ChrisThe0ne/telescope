package dev.bencsik.telescope.controller;

import dev.bencsik.telescope.model.TelemetryData;
import dev.bencsik.telescope.repository.TelemetryRepository;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/telemetry")
public class TelemetryController {

    private final TelemetryRepository telemetryRepository;

    public TelemetryController(TelemetryRepository telemetryRepository) {
        this.telemetryRepository = telemetryRepository;
    }

    @PostMapping
    public TelemetryData storeTelemetry(@RequestBody TelemetryData telemetryData) {
        System.out.println("Received JSON: " + telemetryData);  // Debugging log
        return telemetryRepository.save(telemetryData);
    }

    @GetMapping
    public List<TelemetryData> getTelemetryData() {
        return telemetryRepository.findAll();
    }
}
