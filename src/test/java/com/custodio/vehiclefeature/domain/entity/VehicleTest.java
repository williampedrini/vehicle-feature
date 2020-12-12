package com.custodio.vehiclefeature.domain.entity;

import org.junit.Test;

import static com.custodio.vehiclefeature.util.JSONUtil.fileToBean;
import static java.lang.String.format;
import static org.junit.Assert.assertTrue;

public class VehicleTest {
    private static final String TEST_CASES_BASE_PATH = "/test-case/unit/entity/vehicle/%s";

    /**
     * WHEN Vehicle does fulfil requirements for Feature A
     * THEN Return Feature A as installable
     */
    @Test
    public void when_vehicleFulfilRequirementsForFeatureA_Then_ShouldReturnFeatureAAsInstallable() {
        //given
        final var featureFullPath = format(TEST_CASES_BASE_PATH, "vehicle-fulfil-requirements-for-feature-a/feature.json");
        final var feature = fileToBean(featureFullPath, Feature.class);
        //when
        final var vehicleFullPath = format(TEST_CASES_BASE_PATH, "vehicle-fulfil-requirements-for-feature-a/vehicle.json");
        final var vehicle = fileToBean(vehicleFullPath, Vehicle.class);
        final var isInstallableFeature = vehicle.isInstallableFeature(feature);
        //then
        assertTrue(isInstallableFeature);
    }

    /**
     * WHEN Vehicle does not fulfil requirements for Feature A
     * THEN Return Feature A as incompatible
     */
    @Test
    public void when_vehicleDoesNotFulfilRequirementsForFeatureA_Then_ShouldReturnFeatureAAsIncompatible() {
        //given
        final var featureFullPath = format(TEST_CASES_BASE_PATH, "vehicle-does-not-fulfil-requirements-for-feature-a/feature.json");
        final var feature = fileToBean(featureFullPath, Feature.class);
        //when
        final var vehicleFullPath = format(TEST_CASES_BASE_PATH, "vehicle-does-not-fulfil-requirements-for-feature-a/vehicle.json");
        final var vehicle = fileToBean(vehicleFullPath, Vehicle.class);
        final var isIncompatibleFeature = vehicle.isIncompatibleFeature(feature);
        //then
        assertTrue(isIncompatibleFeature);
    }
}