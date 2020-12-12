package com.custodio.vehiclefeature.usecase;

import com.custodio.vehiclefeature.usecase.model.VehicleHardware;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.dataformat.csv.CsvMapper;
import lombok.RequiredArgsConstructor;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Component;

import static java.lang.String.format;
import static java.util.Objects.requireNonNull;

@Component
@RequiredArgsConstructor
public class ReadHardwareInformation {
    private final CsvMapper csvMapper;

    /**
     * Read a certain {@link VehicleHardware} from a csv {@link String}.
     *
     * @param source The csv source to be read.
     * @return The {@link VehicleHardware} obtained from the {@link String}.
     */
    @NotNull
    public VehicleHardware read(@NotNull final String source) {
        try {
            requireNonNull(source, "The CSV row is mandatory for the hardware data extraction.");
            return csvMapper.readerWithTypedSchemaFor(VehicleHardware.class)
                           .readValue(source);
        } catch (final JsonProcessingException exception) {
            final var message = format("Error while reading the hardware source %s.", source);
            throw new IllegalArgumentException(message, exception);
        }
    }
}
