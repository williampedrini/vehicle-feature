package com.custodio.vehiclefeature.usecase;

import com.custodio.vehiclefeature.domain.entity.Vehicle;
import com.custodio.vehiclefeature.domain.port.FeatureRepository;
import com.custodio.vehiclefeature.usecase.model.Feature;
import lombok.RequiredArgsConstructor;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Component;

import java.util.Collection;

import static java.util.Objects.requireNonNull;
import static java.util.stream.Collectors.toUnmodifiableList;

@Component
@RequiredArgsConstructor
public class FindFeatureInformation {
    private final FeatureRepository featureRepository;
    private final FindVehicleInformation findVehicleInformation;

    /**
     * Searches for all installable features for a specific {@link Vehicle} by its VIN.
     *
     * @param vin The vehicle identifier.
     * @return A collection containing all the installable {@link Feature}.
     */
    @NotNull
    public Collection<Feature> findAllInstallableByVin(@NotNull final String vin) {
        requireNonNull(vin, "The vehicle VIN is mandatory for the search.");
        final var vehicle = findVehicleInformation.findByVin(vin);
        return featureRepository.findAll()
                       .stream()
                       .filter(vehicle::isInstallableFeature)
                       .map(Feature::new)
                       .sorted()
                       .collect(toUnmodifiableList());
    }

    /**
     * Searches for all incompatible features for a specific {@link Vehicle} by its VIN.
     *
     * @param vin The vehicle identifier.
     * @return A collection containing all the incompatible {@link Feature}.
     */
    @NotNull
    public Collection<Feature> findAllIncompatibleByVin(@NotNull final String vin) {
        requireNonNull(vin, "The vehicle VIN is mandatory for the search.");
        final var vehicle = findVehicleInformation.findByVin(vin);
        return featureRepository.findAll()
                       .stream()
                       .filter(vehicle::isIncompatibleFeature)
                       .map(Feature::new)
                       .sorted()
                       .collect(toUnmodifiableList());
    }
}
