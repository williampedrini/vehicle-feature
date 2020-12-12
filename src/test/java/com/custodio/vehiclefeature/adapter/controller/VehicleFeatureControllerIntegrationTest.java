package com.custodio.vehiclefeature.adapter.controller;

import com.custodio.vehiclefeature.adapter.controller.model.Feature;
import com.fasterxml.jackson.core.type.TypeReference;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

import static com.custodio.vehiclefeature.util.JSONUtil.fileToBean;
import static java.lang.String.format;
import static java.util.Arrays.asList;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;

@RunWith(SpringRunner.class)
@SpringBootTest(
        webEnvironment = RANDOM_PORT,
        properties = {
                "spring.datasource.url=jdbc:h2:mem:vehicle-feature;MODE=MYSQL;DB_CLOSE_ON_EXIT=FALSE",
                "spring.datasource.driverClassName=org.h2.Driver",
                "spring.liquibase.enabled=false"
        }
)
public class VehicleFeatureControllerIntegrationTest {
    private static final String TEST_CASES_BASE_PATH = "/test-case/integration/adapter/controller/vehicle-feature/%s";

    @Autowired
    private TestRestTemplate client;

    @Test
    @Sql("classpath:test-case/integration/adapter/controller/vehicle-feature/when-there-are-installable-features-for-vehicle/input.sql")
    public void when_ThereIsVehicleForVIN_And_VehicleFulfilRequirementsForFeatureA_Then_ReturnFeatureAAsInstallable() {
        //when
        final var response = client.getForEntity("/vehicles/3C3CFFER4ET929645/installable", Feature[].class);
        final var actualInstallableFeatures = asList(response.getBody());
        //then
        assertFalse(actualInstallableFeatures.isEmpty());
        final var expectedInstallableFeaturesFullPath = format(TEST_CASES_BASE_PATH, "when-there-are-installable-features-for-vehicle/expected-features.json");
        final var expectedInstallableFeatures = fileToBean(expectedInstallableFeaturesFullPath, new TypeReference<List<Feature>>() {
        });
        assertEquals(expectedInstallableFeatures, actualInstallableFeatures);
    }
}