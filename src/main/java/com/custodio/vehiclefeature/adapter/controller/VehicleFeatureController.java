package com.custodio.vehiclefeature.adapter.controller;

import com.custodio.vehiclefeature.adapter.controller.model.ErrorResponse;
import com.custodio.vehiclefeature.adapter.controller.model.Feature;
import com.custodio.vehiclefeature.usecase.FindFeatureInformation;
import com.custodio.vehiclefeature.usecase.validator.VIN;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.Collection;

import static java.util.stream.Collectors.toUnmodifiableList;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@Api(tags = "Vehicle Installable/incompatible features")
@RestController
@RequestMapping("vehicles")
@RequiredArgsConstructor
@Validated
class VehicleFeatureController {
    private final FindFeatureInformation findFeatureInformation;

    @ApiOperation(
            produces = APPLICATION_JSON_VALUE,
            value = "Gives all the features that can be installed for the corresponding vin."
    )
    @ApiResponses({
            @ApiResponse(code = 200, message = "Success installable feature retrieval.", responseContainer = "List", response = Feature.class),
            @ApiResponse(code = 400, message = "Error while performing pre-validations against vin number.", response = ErrorResponse.class),
            @ApiResponse(code = 404, message = "There is not any vehicle for the defined VIN.", response = ErrorResponse.class)
    })
    @GetMapping("{vin}/installable")
    Collection<Feature> findAllInstallableByVin(@ApiParam(example = "3C3CFFER4ET929645", format = "uuid", required = true, value = "The vehicle identification number.")
                                                @PathVariable @Valid @VIN final String vin) {
        return findFeatureInformation.findAllInstallableByVin(vin)
                       .stream()
                       .map(Feature::new)
                       .collect(toUnmodifiableList());
    }

    @ApiOperation(
            produces = APPLICATION_JSON_VALUE,
            value = "Gives all the features that cannot be installed for the corresponding vin."
    )
    @ApiResponses({
            @ApiResponse(code = 200, message = "Success incompatible feature retrieval.", responseContainer = "List", response = Feature.class),
            @ApiResponse(code = 400, message = "Error while performing pre-validations against vin number.", response = ErrorResponse.class),
            @ApiResponse(code = 404, message = "There is not any vehicle for the defined VIN.", response = ErrorResponse.class)
    })
    @GetMapping("{vin}/incompatible")
    Collection<Feature> findAllIncompatibleByVin(@ApiParam(example = "3C3CFFER4ET929645", format = "uuid", required = true, value = "The vehicle identification number.")
                                                 @PathVariable @Valid @VIN final String vin) {
        return findFeatureInformation.findAllIncompatibleByVin(vin)
                       .stream()
                       .map(Feature::new)
                       .collect(toUnmodifiableList());
    }
}
