package com.custodio.vehiclefeature.usecase;

import com.custodio.vehiclefeature.domain.port.HardwareRepository;
import com.custodio.vehiclefeature.domain.port.VehicleRepository;
import com.custodio.vehiclefeature.usecase.model.VehicleHardware;
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
public class SaveHardwareInformation {
    private final VehicleRepository vehicleRepository;
    private final HardwareRepository hardwareRepository;

    /**
     * Persists a certain {@link VehicleHardware} information.
     *
     * @param vehicleHardware The vehicle hardware information.
     */
    public void save(@NotNull @Valid final VehicleHardware vehicleHardware) {
        final var hardware = hardwareRepository
                                     .findByCode(vehicleHardware.getCode())
                                     .orElseGet(vehicleHardware::toHardware);
        final var vehicle = vehicleRepository
                                    .findByVin(vehicleHardware.getVin())
                                    .orElseGet(vehicleHardware::toVehicle)
                                    .add(hardware);
        vehicleRepository.save(vehicle);
        log.info("Reading vehicle hardware information {}.", vehicleHardware);
    }
}
