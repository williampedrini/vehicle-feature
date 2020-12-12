package com.custodio.vehiclefeature.domain.port;

import com.custodio.vehiclefeature.domain.entity.Feature;
import org.jetbrains.annotations.NotNull;

import java.util.Collection;

public interface FeatureRepository {

    /**
     * Search for all existing {@link Feature}.
     *
     * @return All existing {@link Feature}
     */
    @NotNull
    Collection<Feature> findAll();
}
