package com.custodio.vehiclefeature.adapter.file.hardware;

import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import java.io.File;
import java.util.Optional;
import java.util.function.Function;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static com.custodio.vehiclefeature.adapter.file.hardware.HardwareChannel.INPUT;
import static java.util.Objects.requireNonNull;

@Component
@Profile("vehicle-data-flow")
class HardwareRouter implements Function<File, Optional<String>> {
    private final Pattern filePattern;

    HardwareRouter(@NotNull @Value("${application.integration.hardware-file-pattern}") final Pattern filePattern) {
        this.filePattern = requireNonNull(filePattern);
    }

    @NotNull
    @Override
    public Optional<String> apply(@NotNull final File file) {
        return Optional.of(file)
                       .map(File::getName)
                       .map(filePattern::matcher)
                       .filter(Matcher::matches)
                       .map(matcher -> INPUT);
    }
}
