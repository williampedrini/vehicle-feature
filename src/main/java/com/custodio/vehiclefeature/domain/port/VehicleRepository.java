package com.custodio.vehiclefeature.domain.port;

import com.custodio.vehiclefeature.domain.entity.Vehicle;
import org.jetbrains.annotations.NotNull;

import java.util.Optional;

public interface VehicleRepository {

    /**
     * Persists a certain vehicle into a certain database.
     * @param vehicle The vehicle to be persisted.
     */
    @NotNull
    Vehicle save(@NotNull Vehicle vehicle);

    /**
     * Search for a certain {@link Vehicle} by its VIN code.
     * @param vin The VIN code used as identifier.
     * @return A possible vehicle if found.
     */
    @NotNull
    Optional<Vehicle> findByVin(@NotNull String vin);
}
