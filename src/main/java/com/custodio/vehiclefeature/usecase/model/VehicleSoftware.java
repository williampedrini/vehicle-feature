package com.custodio.vehiclefeature.usecase.model;

import com.custodio.vehiclefeature.domain.entity.Software;
import com.custodio.vehiclefeature.domain.entity.Vehicle;
import com.custodio.vehiclefeature.usecase.validator.VIN;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.Data;

import javax.validation.constraints.NotEmpty;

@Data
@JsonPropertyOrder({"vin", "code"})
public class VehicleSoftware {
    @NotEmpty(message = "The vehicle VIN code is mandatory.")
    @VIN
    private String vin;

    @NotEmpty(message = "The software code is mandatory.")
    private String code;

    /**
     * Converts the current {@link VehicleSoftware} into a {@link Vehicle}.
     *
     * @return The {@link Vehicle} containing the current information.
     */
    public Vehicle toVehicle() {
        return Vehicle.builder()
                       .vin(vin)
                       .build();
    }

    /**
     * Converts the current {@link VehicleSoftware} into a {@link Software}.
     *
     * @return The {@link Software} containing the current information.
     */
    public Software toSoftware() {
        return Software.builder()
                       .code(code)
                       .build();
    }
}