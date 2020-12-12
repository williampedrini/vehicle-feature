package com.custodio.vehiclefeature.adapter.file.hardware;

import com.custodio.vehiclefeature.usecase.ReadHardwareInformation;
import com.custodio.vehiclefeature.usecase.model.VehicleHardware;
import lombok.RequiredArgsConstructor;
import org.jetbrains.annotations.NotNull;
import org.springframework.context.annotation.Profile;
import org.springframework.integration.annotation.Transformer;
import org.springframework.stereotype.Component;

import static com.custodio.vehiclefeature.adapter.file.hardware.HardwareChannel.SPLITTED;
import static com.custodio.vehiclefeature.adapter.file.hardware.HardwareChannel.TRANSFORMED;
import static java.util.Objects.requireNonNull;

@Component
@Profile("vehicle-data-flow")
@RequiredArgsConstructor
class HardwareTranslator {
    private final ReadHardwareInformation readHardwareInformation;

    @NotNull
    @Transformer(inputChannel = SPLITTED, outputChannel = TRANSFORMED)
    VehicleHardware transform(@NotNull final String csvRow) {
        requireNonNull(csvRow, "The csv row is mandatory to generate hardware information.");
        return readHardwareInformation.read(csvRow);
    }
}
