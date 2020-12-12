package com.custodio.vehiclefeature.adapter.controller.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

@ApiModel(description = "Error response")
@EqualsAndHashCode
@Getter
@ToString
public class ErrorResponse {
    @ApiModelProperty(value = "The reason why an error occurred.", example = "The query was not performed with success.")
    private final String reason;

    @JsonCreator
    public ErrorResponse(@JsonProperty("reason") final String reason) {
        this.reason = reason;
    }
}
