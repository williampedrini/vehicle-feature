package com.custodio.vehiclefeature.adapter.file.unknown;

import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.NotNull;
import org.springframework.integration.annotation.ServiceActivator;
import org.springframework.messaging.MessagingException;
import org.springframework.stereotype.Component;

import java.io.File;

import static com.custodio.vehiclefeature.adapter.file.unknown.UnknownFileChannel.INPUT;

@Component
@Slf4j
class UnknownFileMessageHandler {

    @ServiceActivator(inputChannel = INPUT)
    void handleMessage(@NotNull final File file) throws MessagingException {
        log.error("Unknown handler for file with name {}", file.getName());
    }
}
