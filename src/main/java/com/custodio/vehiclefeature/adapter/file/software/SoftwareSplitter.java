package com.custodio.vehiclefeature.adapter.file.software;

import lombok.RequiredArgsConstructor;
import org.jetbrains.annotations.NotNull;
import org.springframework.context.annotation.Profile;
import org.springframework.integration.annotation.Splitter;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.IOException;
import java.util.Collection;

import static com.custodio.vehiclefeature.adapter.file.software.SoftwareChannel.INPUT;
import static com.custodio.vehiclefeature.adapter.file.software.SoftwareChannel.SPLITTED;
import static java.nio.file.Files.readAllLines;
import static java.util.Objects.requireNonNull;

@Component
@Profile("vehicle-data-flow")
@RequiredArgsConstructor
class SoftwareSplitter {

    @NotNull
    @Splitter(inputChannel = INPUT, outputChannel = SPLITTED)
    Collection<String> split(@NotNull final File file) throws IOException {
        requireNonNull(file, "The file is mandatory to generate software information.");
        return readAllLines(file.toPath());
    }
}
