package com.custodio.vehiclefeature.domain.port;

import com.custodio.vehiclefeature.domain.entity.Hardware;
import org.jetbrains.annotations.NotNull;

import java.util.Optional;

public interface HardwareRepository {

    /**
     * Search for a certain {@link Hardware} by its code.
     * @param code The code used as identifier.
     * @return A possible hardware if found.
     */
    @NotNull
    Optional<Hardware> findByCode(@NotNull String code);
}
