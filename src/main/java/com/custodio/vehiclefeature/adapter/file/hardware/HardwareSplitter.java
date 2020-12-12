package com.custodio.vehiclefeature.adapter.file.hardware;

import lombok.RequiredArgsConstructor;
import org.jetbrains.annotations.NotNull;
import org.springframework.context.annotation.Profile;
import org.springframework.integration.annotation.Splitter;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.Collection;

import static com.custodio.vehiclefeature.adapter.file.hardware.HardwareChannel.INPUT;
import static com.custodio.vehiclefeature.adapter.file.hardware.HardwareChannel.SPLITTED;
import static java.util.Objects.requireNonNull;

@Component
@Profile("vehicle-data-flow")
@RequiredArgsConstructor
class HardwareSplitter {

    @NotNull
    @Splitter(inputChannel = INPUT, outputChannel = SPLITTED)
    Collection<String> split(@NotNull final File file) throws IOException {
        requireNonNull(file, "The file is mandatory to generate hardware information.");
        return Files.readAllLines(file.toPath());
    }
}
