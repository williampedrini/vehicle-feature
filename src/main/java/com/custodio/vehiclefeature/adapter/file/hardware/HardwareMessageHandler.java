package com.custodio.vehiclefeature.adapter.file.hardware;

import com.custodio.vehiclefeature.usecase.SaveHardwareInformation;
import com.custodio.vehiclefeature.usecase.model.VehicleHardware;
import lombok.RequiredArgsConstructor;
import org.jetbrains.annotations.NotNull;
import org.springframework.context.annotation.Profile;
import org.springframework.integration.annotation.ServiceActivator;
import org.springframework.messaging.MessagingException;
import org.springframework.stereotype.Component;

import static com.custodio.vehiclefeature.adapter.file.hardware.HardwareChannel.TRANSFORMED;
import static java.util.Objects.requireNonNull;

@Component
@Profile("vehicle-data-flow")
@RequiredArgsConstructor
class HardwareMessageHandler {
    private final SaveHardwareInformation saveHardwareInformation;

    @ServiceActivator(inputChannel = TRANSFORMED)
    void handleMessage(@NotNull final VehicleHardware hardware) throws MessagingException {
        requireNonNull(hardware, "The hardware information is mandatory.");
        saveHardwareInformation.save(hardware);
    }
}
