package com.custodio.vehiclefeature.adapter.file.software;

import com.custodio.vehiclefeature.usecase.ReadSoftwareInformation;
import com.custodio.vehiclefeature.usecase.model.VehicleSoftware;
import lombok.RequiredArgsConstructor;
import org.jetbrains.annotations.NotNull;
import org.springframework.context.annotation.Profile;
import org.springframework.integration.annotation.Transformer;
import org.springframework.stereotype.Component;

import static com.custodio.vehiclefeature.adapter.file.software.SoftwareChannel.SPLITTED;
import static com.custodio.vehiclefeature.adapter.file.software.SoftwareChannel.TRANSFORMED;
import static java.util.Objects.requireNonNull;

@Component
@Profile("vehicle-data-flow")
@RequiredArgsConstructor
class SoftwareTranslator {
    private final ReadSoftwareInformation readSoftwareInformation;

    @NotNull
    @Transformer(inputChannel = SPLITTED, outputChannel = TRANSFORMED)
    VehicleSoftware transform(@NotNull final String csvRow) {
        requireNonNull(csvRow, "The csv row is mandatory to generate software information.");
        return readSoftwareInformation.read(csvRow);
    }
}
