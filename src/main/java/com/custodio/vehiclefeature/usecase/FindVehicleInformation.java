package com.custodio.vehiclefeature.usecase;

import com.custodio.vehiclefeature.domain.entity.Vehicle;
import com.custodio.vehiclefeature.domain.port.VehicleRepository;
import lombok.RequiredArgsConstructor;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Component;

import javax.persistence.EntityNotFoundException;

import static java.lang.String.format;
import static java.util.Objects.requireNonNull;

@Component
@RequiredArgsConstructor
public class FindVehicleInformation {
    private final VehicleRepository vehicleRepository;

    /**
     * Search for a certain {@link Vehicle} by its VIN code.
     *
     * @param vin The VIN code used as identifier.
     * @return The found {@link Vehicle}.
     * @throws EntityNotFoundException If there is not any vehicle for the provided VIN.
     */
    @NotNull
    public Vehicle findByVin(@NotNull final String vin) {
        requireNonNull(vin, "The vehicle VIN is mandatory for the search.");
        return vehicleRepository.findByVin(vin)
                       .orElseThrow(() -> {
                           final var message = format("There is not any vehicle for the VIN %s.", vin);
                           return new EntityNotFoundException(message);
                       });
    }
}
