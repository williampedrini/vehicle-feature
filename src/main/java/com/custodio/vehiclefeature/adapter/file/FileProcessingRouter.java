package com.custodio.vehiclefeature.adapter.file;

import com.custodio.vehiclefeature.adapter.file.unknown.UnknownFileChannel;
import lombok.RequiredArgsConstructor;
import org.jetbrains.annotations.NotNull;
import org.springframework.context.annotation.Profile;
import org.springframework.integration.annotation.Router;
import org.springframework.stereotype.Component;

import java.io.File;
import java.util.Optional;
import java.util.function.Function;

import static com.custodio.vehiclefeature.adapter.file.FileProcessingFlowConfiguration.INPUT_CHANNEL;

@Component
@Profile("vehicle-data-flow")
@RequiredArgsConstructor
class FileProcessingRouter {
    private final Function<File, Optional<String>> hardwareRouter;
    private final Function<File, Optional<String>> softwareRouter;

    @NotNull
    @Router(inputChannel = INPUT_CHANNEL)
    String route(@NotNull final File file) {
        return hardwareRouter.apply(file)
                       .orElseGet(() -> softwareRouter
                                                .apply(file)
                                                .orElse(UnknownFileChannel.INPUT));
    }
}
