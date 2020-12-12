package com.custodio.vehiclefeature.usecase;

import com.custodio.vehiclefeature.domain.port.SoftwareRepository;
import com.custodio.vehiclefeature.domain.port.VehicleRepository;
import com.custodio.vehiclefeature.usecase.model.VehicleSoftware;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;

import javax.validation.Valid;

@Component
@RequiredArgsConstructor
@Slf4j
@Transactional
@Validated
public class SaveSoftwareInformation {
    private final VehicleRepository vehicleRepository;
    private final SoftwareRepository hardwareRepository;

    /**
     * Persists a certain {@link VehicleSoftware} information.
     *
     * @param vehicleSoftware The vehicle software information.
     */
    public void save(@NotNull @Valid final VehicleSoftware vehicleSoftware) {
        final var software = hardwareRepository
                                     .findByCode(vehicleSoftware.getCode())
                                     .orElseGet(vehicleSoftware::toSoftware);
        final var vehicle = vehicleRepository
                                    .findByVin(vehicleSoftware.getVin())
                                    .orElseGet(vehicleSoftware::toVehicle)
                                    .add(software);
        vehicleRepository.save(vehicle);
        log.info("Reading vehicle software information {}.", vehicleSoftware);
    }
}
