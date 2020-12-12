package com.custodio.vehiclefeature.adapter.file.software;

import com.custodio.vehiclefeature.usecase.SaveSoftwareInformation;
import com.custodio.vehiclefeature.usecase.model.VehicleSoftware;
import lombok.RequiredArgsConstructor;
import org.jetbrains.annotations.NotNull;
import org.springframework.context.annotation.Profile;
import org.springframework.integration.annotation.ServiceActivator;
import org.springframework.messaging.MessagingException;
import org.springframework.stereotype.Component;

import static com.custodio.vehiclefeature.adapter.file.software.SoftwareChannel.TRANSFORMED;
import static java.util.Objects.requireNonNull;

@Component
@Profile("vehicle-data-flow")
@RequiredArgsConstructor
class SoftwareMessageHandler {
    private final SaveSoftwareInformation saveSoftwareInformation;

    @ServiceActivator(inputChannel = TRANSFORMED)
    void handleMessage(@NotNull final VehicleSoftware software) throws MessagingException {
        requireNonNull(software, "The software information is mandatory.");
        saveSoftwareInformation.save(software);
    }
}
