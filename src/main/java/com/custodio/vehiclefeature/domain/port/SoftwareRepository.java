package com.custodio.vehiclefeature.domain.port;

import com.custodio.vehiclefeature.domain.entity.Software;
import org.jetbrains.annotations.NotNull;

import java.util.Optional;

public interface SoftwareRepository {

    /**
     * Search for a certain {@link Software} by its code.
     *
     * @param code The code used as identifier.
     * @return A possible hardware if found.
     */
    @NotNull
    Optional<Software> findByCode(@NotNull String code);
}
