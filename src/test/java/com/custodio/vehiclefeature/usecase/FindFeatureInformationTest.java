package com.custodio.vehiclefeature.usecase;

import com.custodio.vehiclefeature.domain.entity.Feature;
import com.custodio.vehiclefeature.domain.entity.Vehicle;
import com.custodio.vehiclefeature.domain.port.FeatureRepository;
import com.fasterxml.jackson.core.type.TypeReference;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.List;

import static com.custodio.vehiclefeature.util.JSONUtil.fileToBean;
import static java.lang.String.format;
import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
@SuppressWarnings("AnonymousInnerClassMayBeStatic")
public class FindFeatureInformationTest {
    private static final String TEST_CASES_BASE_PATH = "/test-case/unit/use-case/find-feature-information/%s";

    @Mock
    private FeatureRepository featureRepository;
    @Mock
    private FindVehicleInformation findVehicleInformation;
    @InjectMocks
    private FindFeatureInformation underTest;

    /**
     * WHEN There is a vehicle for VIN
     * AND Vehicle does match all requirements for features
     * THEN Return all installable features.
     */
    @Test
    public void when_ThereIsVehicleForVIN_And_VehicleHasInstallableFeature_Then_ShouldReturnAllInstallableFeatures() {
        //given
        final var mockedVehicleFullPath = format(TEST_CASES_BASE_PATH, "when-there-is-vehicle-for-vin-and-vehicle-has-installable-feature/mocked-vehicle.json");
        final var mockedVehicle = fileToBean(mockedVehicleFullPath, Vehicle.class);
        when(findVehicleInformation.findByVin(mockedVehicle.getVin())).thenReturn(mockedVehicle);

        final var mockedFeaturesFullPath = format(TEST_CASES_BASE_PATH, "when-there-is-vehicle-for-vin-and-vehicle-has-installable-feature/mocked-features.json");
        final var mockedFeatures = fileToBean(mockedFeaturesFullPath, new TypeReference<List<Feature>>() {
        });
        when(featureRepository.findAll()).thenReturn(mockedFeatures);

        //when
        final var actualInstallableFeatures = underTest.findAllInstallableByVin(mockedVehicle.getVin());

        //then
        final var expectedInstallableFeaturesFullPath = format(TEST_CASES_BASE_PATH, "when-there-is-vehicle-for-vin-and-vehicle-has-installable-feature/expected-features.json");
        final var expectedInstallableFeatures = fileToBean(expectedInstallableFeaturesFullPath, new TypeReference<List<com.custodio.vehiclefeature.usecase.model.Feature>>() {
        });
        assertEquals(expectedInstallableFeatures, actualInstallableFeatures);
    }

    /**
     * WHEN There is a vehicle for VIN
     * AND Vehicle does not match any requirement
     * THEN Return all features as incompatible
     */
    @Test
    public void when_ThereIsVehicleForVIN_And_VehicleHasIncompatibleFeature_Then_ShouldReturnAllIncompatibleFeatures() {
        //given
        final var mockedVehicleFullPath = format(TEST_CASES_BASE_PATH, "when-there-is-vehicle-for-vin-and-vehicle-has-incompatible-feature/mocked-vehicle.json");
        final var mockedVehicle = fileToBean(mockedVehicleFullPath, Vehicle.class);
        when(findVehicleInformation.findByVin(mockedVehicle.getVin())).thenReturn(mockedVehicle);

        final var mockedFeaturesFullPath = format(TEST_CASES_BASE_PATH, "when-there-is-vehicle-for-vin-and-vehicle-has-incompatible-feature/mocked-features.json");
        final var mockedFeatures = fileToBean(mockedFeaturesFullPath, new TypeReference<List<Feature>>() {
        });
        when(featureRepository.findAll()).thenReturn(mockedFeatures);

        //when
        final var actualIncompatibleFeatures = underTest.findAllIncompatibleByVin(mockedVehicle.getVin());

        //then
        final var expectedIncompatibleFeaturesFullPath = format(TEST_CASES_BASE_PATH, "when-there-is-vehicle-for-vin-and-vehicle-has-incompatible-feature/expected-features.json");
        final var expectedIncompatibleFeatures = fileToBean(expectedIncompatibleFeaturesFullPath, new TypeReference<List<com.custodio.vehiclefeature.usecase.model.Feature>>() {
        });
        assertEquals(expectedIncompatibleFeatures, actualIncompatibleFeatures);
    }

    /**
     * WHEN There is a vehicle for VIN
     * AND Vehicle has all included requirements for software and hardware
     * AND Vehicle has an excluded requirement for software
     * THEN Return feature as incompatible
     */
    @Test
    public void when_ThereIsVehicleForVIN_And_VehicleHasAllRequirementsForFeature_And_HasExcludedRequirementForFeature_Then_ShouldReturnFeatureAsIncompatible() {
        //given
        final var mockedVehicleFullPath = format(TEST_CASES_BASE_PATH, "when-there-is-vehicle-for-vin-and-vehicle-has-all-requirements-for-feature-and-has-excluded-requirement-for-feature/mocked-vehicle.json");
        final var mockedVehicle = fileToBean(mockedVehicleFullPath, Vehicle.class);
        when(findVehicleInformation.findByVin(mockedVehicle.getVin())).thenReturn(mockedVehicle);

        final var mockedFeaturesFullPath = format(TEST_CASES_BASE_PATH, "when-there-is-vehicle-for-vin-and-vehicle-has-all-requirements-for-feature-and-has-excluded-requirement-for-feature/mocked-features.json");
        final var mockedFeatures = fileToBean(mockedFeaturesFullPath, new TypeReference<List<Feature>>() {
        });
        when(featureRepository.findAll()).thenReturn(mockedFeatures);

        //when
        final var actualIncompatibleFeatures = underTest.findAllIncompatibleByVin(mockedVehicle.getVin());

        //then
        final var expectedIncompatibleFeaturesFullPath = format(TEST_CASES_BASE_PATH, "when-there-is-vehicle-for-vin-and-vehicle-has-all-requirements-for-feature-and-has-excluded-requirement-for-feature/expected-features.json");
        final var expectedIncompatibleFeatures = fileToBean(expectedIncompatibleFeaturesFullPath, new TypeReference<List<com.custodio.vehiclefeature.usecase.model.Feature>>() {
        });
        assertEquals(expectedIncompatibleFeatures, actualIncompatibleFeatures);
    }
}
