package dev.bencsik.telescope.repository;

import dev.bencsik.telescope.model.TelemetryData;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface TelemetryRepository extends JpaRepository<TelemetryData, UUID> {
}