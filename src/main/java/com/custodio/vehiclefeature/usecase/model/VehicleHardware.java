package com.custodio.vehiclefeature.usecase.model;

import com.custodio.vehiclefeature.domain.entity.Hardware;
import com.custodio.vehiclefeature.domain.entity.Vehicle;
import com.custodio.vehiclefeature.usecase.validator.VIN;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.Data;

import javax.validation.constraints.NotEmpty;

@Data
@JsonPropertyOrder({"vin", "code"})
public class VehicleHardware {
    @NotEmpty(message = "The vehicle VIN code is mandatory.")
    @VIN
    private String vin;

    @NotEmpty(message = "The hardware code is mandatory.")
    private String code;

    /**
     * Converts the current {@link VehicleHardware} into a {@link Vehicle}.
     *
     * @return The {@link Vehicle} containing the current information.
     */
    public Vehicle toVehicle() {
        return Vehicle.builder()
                       .vin(vin)
                       .build();
    }

    /**
     * Converts the current {@link VehicleHardware} into a {@link Hardware}.
     *
     * @return The {@link Hardware} containing the current information.
     */
    public Hardware toHardware() {
        return Hardware.builder()
                       .code(code)
                       .build();
    }
}