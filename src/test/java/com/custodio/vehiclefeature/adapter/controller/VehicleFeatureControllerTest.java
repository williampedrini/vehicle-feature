package com.custodio.vehiclefeature.adapter.controller;

import com.custodio.vehiclefeature.adapter.controller.model.Feature;
import com.custodio.vehiclefeature.usecase.FindFeatureInformation;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.json.AutoConfigureJsonTesters;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import javax.persistence.EntityNotFoundException;
import java.util.List;

import static com.custodio.vehiclefeature.util.JSONUtil.fileToBean;
import static java.lang.String.format;
import static java.util.Collections.emptyList;
import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;
import static org.springframework.http.HttpStatus.OK;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

@AutoConfigureJsonTesters
@RunWith(SpringRunner.class)
@WebMvcTest(VehicleFeatureController.class)
public class VehicleFeatureControllerTest {
    private static final String TEST_CASES_BASE_PATH = "/test-case/unit/adapter/controller/vehicle-feature/%s";
    private static final String VEHICLE_VIN = "3C3CFFER4ET929645";

    private final ObjectMapper mapper = new ObjectMapper();
    @Autowired
    private MockMvc mock;
    @MockBean
    private FindFeatureInformation findFeatureInformation;

    /**
     * WHEN There are installable features for vehicle
     * THEN Return installable list of features
     */
    @Test
    public void when_ThereAreInstallableFeaturesForVehicle_Then_ReturnAllInstallableFeatures() throws Exception {
        //given
        final var mockedFeaturesFullPath = format(TEST_CASES_BASE_PATH, "when-there-are-installable-features-for-vehicle/mocked-features.json");
        final var mockedFeatures = fileToBean(mockedFeaturesFullPath, new TypeReference<List<com.custodio.vehiclefeature.usecase.model.Feature>>() {
        });
        when(findFeatureInformation.findAllInstallableByVin(VEHICLE_VIN)).thenReturn(mockedFeatures);

        //when
        final var result = mock.perform(get(format("/vehicles/%s/installable", VEHICLE_VIN))).andReturn();
        final var actualInstallableFeatures = mapper.readValue(result.getResponse().getContentAsByteArray(), new TypeReference<List<Feature>>() {
        });

        //then
        final var expectedFeaturesFullPath = format(TEST_CASES_BASE_PATH, "when-there-are-installable-features-for-vehicle/expected-features.json");
        final var expectedFeatures = fileToBean(expectedFeaturesFullPath, new TypeReference<List<Feature>>() {
        });
        assertEquals(OK.value(), result.getResponse().getStatus());
        assertEquals(expectedFeatures, actualInstallableFeatures);
    }

    /**
     * WHEN There are not installable features for vehicle
     * THEN Return Empty result list
     */
    @Test
    public void when_ThereAreNotInstallableFeaturesForVehicle_Then_ReturnEmptyResult() throws Exception {
        //given
        when(findFeatureInformation.findAllInstallableByVin(VEHICLE_VIN)).thenReturn(emptyList());

        //when
        final var result = mock.perform(get(format("/vehicles/%s/installable", VEHICLE_VIN))).andReturn();
        final var actualInstallableFeatures = mapper.readValue(result.getResponse().getContentAsByteArray(), new TypeReference<List<Feature>>() {
        });

        //then
        assertEquals(OK.value(), result.getResponse().getStatus());
        assertEquals(emptyList(), actualInstallableFeatures);
    }

    /**
     * WHEN There are incompatible features for vehicle
     * THEN Return incompatible list of features
     */
    @Test
    public void when_ThereAreIncompatibleFeaturesForVehicle_Then_ReturnAllIncompatibleFeatures() throws Exception {
        //given
        final var mockedFeaturesFullPath = format(TEST_CASES_BASE_PATH, "when-there-are-incompatible-features-for-vehicle/mocked-features.json");
        final var mockedFeatures = fileToBean(mockedFeaturesFullPath, new TypeReference<List<com.custodio.vehiclefeature.usecase.model.Feature>>() {
        });
        when(findFeatureInformation.findAllIncompatibleByVin(VEHICLE_VIN)).thenReturn(mockedFeatures);

        //when
        final var result = mock.perform(get(format("/vehicles/%s/incompatible", VEHICLE_VIN))).andReturn();
        final var actualInstallableFeatures = mapper.readValue(result.getResponse().getContentAsByteArray(), new TypeReference<List<Feature>>() {
        });

        //then
        final var expectedFeaturesFullPath = format(TEST_CASES_BASE_PATH, "when-there-are-incompatible-features-for-vehicle/expected-features.json");
        final var expectedFeatures = fileToBean(expectedFeaturesFullPath, new TypeReference<List<Feature>>() {
        });
        assertEquals(OK.value(), result.getResponse().getStatus());
        assertEquals(expectedFeatures, actualInstallableFeatures);
    }

    /**
     * WHEN There are not incompatible features for vehicle
     * THEN Return Empty result list
     */
    @Test
    public void when_ThereAreNotIncompatibleFeaturesForVehicle_Then_ReturnEmptyResult() throws Exception {
        //given
        when(findFeatureInformation.findAllInstallableByVin(VEHICLE_VIN)).thenReturn(emptyList());

        //when
        final var result = mock.perform(get(format("/vehicles/%s/incompatible", VEHICLE_VIN))).andReturn();
        final var actualInstallableFeatures = mapper.readValue(result.getResponse().getContentAsByteArray(), new TypeReference<List<Feature>>() {
        });

        //then
        assertEquals(OK.value(), result.getResponse().getStatus());
        assertEquals(emptyList(), actualInstallableFeatures);
    }
}