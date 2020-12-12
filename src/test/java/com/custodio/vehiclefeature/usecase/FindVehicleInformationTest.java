package com.custodio.vehiclefeature.usecase;

import com.custodio.vehiclefeature.domain.entity.Vehicle;
import com.custodio.vehiclefeature.domain.port.VehicleRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import javax.persistence.EntityNotFoundException;

import static com.custodio.vehiclefeature.util.JSONUtil.fileToBean;
import static java.lang.String.format;
import static java.util.Optional.empty;
import static java.util.Optional.of;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThrows;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class FindVehicleInformationTest {
    private static final String TEST_CASES_BASE_PATH = "/test-case/unit/use-case/find-vehicle-information/%s";

    @Mock
    private VehicleRepository vehicleRepository;
    @InjectMocks
    private FindVehicleInformation underTest;

    /**
     * WHEN There is not any vehicle for VIN
     * THEN Throw entity not found exception.
     */
    @Test
    public void when_ThereIsNotAnyVehicleForVIN_Then_ShouldThrowEntityNotFoundException() {
        //given
        when(vehicleRepository.findByVin(anyString())).thenReturn(empty());
        //then
        assertThrows(EntityNotFoundException.class, () -> {
            //when
            underTest.findByVin("3C3CFFER4ET929645");
        });
    }

    /**
     * WHEN provided VIN is null
     * THEN Throw null pointer exception with message
     */
    @Test
    public void when_ProvidedVINIsNull_Then_ShouldThrowNullPointException() {
        //then
        assertThrows("The vehicle VIN is mandatory for the search.", NullPointerException.class, () -> {
            //when
            underTest.findByVin(null);
        });
    }

    /**
     * WHEN There is vehicle for VIN
     * THEN Return found vehicle
     */
    @Test
    public void when_ThereIsVehicleForVIN_Then_ShouldReturnFoundVehicleForVIN() {
        //given
        final var mockedVehicleFullPath = format(TEST_CASES_BASE_PATH, "when-there-is-vehicle-for-vin/mocked-vehicle.json");
        final var mockedVehicle = fileToBean(mockedVehicleFullPath, Vehicle.class);
        when(vehicleRepository.findByVin(mockedVehicle.getVin())).thenReturn(of(mockedVehicle));

        //when
        final var actualVehicle = underTest.findByVin(mockedVehicle.getVin());

        //then
        final var expectedVehicleFullPath = format(TEST_CASES_BASE_PATH, "when-there-is-vehicle-for-vin/expected-vehicle.json");
        final var expectedVehicle = fileToBean(expectedVehicleFullPath, Vehicle.class);
        assertEquals(expectedVehicle, actualVehicle);
    }
}