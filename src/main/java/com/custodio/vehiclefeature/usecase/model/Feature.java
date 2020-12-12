package com.custodio.vehiclefeature.usecase.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;
import org.jetbrains.annotations.NotNull;

import static java.util.Objects.requireNonNull;

@EqualsAndHashCode
@Getter
@ToString
public class Feature implements Comparable<Feature> {
    private final Long id;
    private final String name;

    public Feature(@NotNull final com.custodio.vehiclefeature.domain.entity.Feature feature) {
        requireNonNull(feature, "The base feature is mandatory for the creation of a feature.");
        id = requireNonNull(feature.getId(), "The id is mandatory for the creation of a feature");
        name = requireNonNull(feature.getName(), "The name is mandatory for the creation of a feature");
    }

    @JsonCreator
    public Feature(@JsonProperty("id") @NotNull final Long id,
                   @JsonProperty("name") @NotNull final String name) {
        this.id = requireNonNull(id, "The id is mandatory for the creation of a feature");
        this.name = requireNonNull(name, "The name is mandatory for the creation of a feature");
    }

    @Override
    public int compareTo(@NotNull final Feature other) {
        return name.compareTo(other.name);
    }
}
