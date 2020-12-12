package com.custodio.vehiclefeature.adapter.controller.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;
import org.jetbrains.annotations.NotNull;

import static java.util.Objects.requireNonNull;

@ApiModel(description = "Vehicle feature")
@EqualsAndHashCode
@Getter
@ToString
public class Feature {
    @ApiModelProperty(value = "The feature identifier.", example = "1")
    private final Long id;
    @ApiModelProperty(value = "The name of the feature.", example = "Feature A")
    private final String name;

    public Feature(@NotNull final com.custodio.vehiclefeature.usecase.model.Feature feature) {
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
}
