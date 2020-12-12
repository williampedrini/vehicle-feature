package com.custodio.vehiclefeature.usecase;

import com.custodio.vehiclefeature.domain.entity.Software;
import com.custodio.vehiclefeature.domain.entity.Vehicle;
import com.custodio.vehiclefeature.domain.port.SoftwareRepository;
import com.custodio.vehiclefeature.domain.port.VehicleRepository;
import com.custodio.vehiclefeature.usecase.model.VehicleSoftware;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import static com.custodio.vehiclefeature.util.JSONUtil.fileToBean;
import static java.lang.String.format;
import static java.util.Optional.empty;
import static java.util.Optional.of;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class SaveSoftwareInformationTest {
    private static final String TEST_CASES_BASE_PATH = "/test-case/unit/use-case/save-software-information/%s";

    @Mock
    private VehicleRepository vehicleRepository;
    @Mock
    private SoftwareRepository softwareRepository;
    @InjectMocks
    private SaveSoftwareInformation underTest;

    /**
     * WHEN There is not any software for code
     * AND There is not any vehicle for VIN
     * THEN persist a new version of the vehicle and software.
     */
    @Test
    public void when_ThereIsNotAnySoftwareForCode_And_ThereIsNotAnyVehicleForVIN_Then_PersistsNewVehicleAndSoftware() {
        //given
        when(softwareRepository.findByCode("GdS6TI")).thenReturn(empty());
        when(vehicleRepository.findByVin("WAUHFBFL1DN549274")).thenReturn(empty());

        //when
        final var inputSoftwareInformationFullPath = format(TEST_CASES_BASE_PATH, "when-there-is-not-any-software-for-code-and-there-is-not-any-vehicle-for-vin/input-software-information.json");
        final var inputSoftwareInformation = fileToBean(inputSoftwareInformationFullPath, VehicleSoftware.class);
        underTest.save(inputSoftwareInformation);

        //then
        final var expectedPersistedVehicleFullPath = format(TEST_CASES_BASE_PATH, "when-there-is-not-any-software-for-code-and-there-is-not-any-vehicle-for-vin/expected-persisted-vehicle.json");
        final var expectedPersistedVehicle = fileToBean(expectedPersistedVehicleFullPath, Vehicle.class);
        verify(vehicleRepository, times(1)).save(expectedPersistedVehicle);
    }

    /**
     * WHEN There is not any hardware for code
     * AND There is a vehicle for VIN
     * THEN Update the existing vehicle
     * AND Create a new version of the software
     */
    @Test
    public void when_ThereIsNotAnySoftwareForCode_And_ThereIsVehicleForVIN_Then_UpdateNewVehicleAndCreateNewSoftware() {
        //given
        when(softwareRepository.findByCode("GdS6TI")).thenReturn(empty());

        final var mockedVehicleFullPath = format(TEST_CASES_BASE_PATH, "when-there-is-not-any-software-for-code-and-there-is-vehicle-for-vin/mocked-vehicle.json");
        final var mockedVehicle = fileToBean(mockedVehicleFullPath, Vehicle.class);
        when(vehicleRepository.findByVin(mockedVehicle.getVin())).thenReturn(of(mockedVehicle));

        //when
        final var inputSoftwareInformationFullPath = format(TEST_CASES_BASE_PATH, "when-there-is-not-any-software-for-code-and-there-is-vehicle-for-vin/input-software-information.json");
        final var inputSoftwareInformation = fileToBean(inputSoftwareInformationFullPath, VehicleSoftware.class);
        underTest.save(inputSoftwareInformation);

        //then
        final var expectedPersistedVehicleFullPath = format(TEST_CASES_BASE_PATH, "when-there-is-not-any-software-for-code-and-there-is-vehicle-for-vin/expected-persisted-vehicle.json");
        final var expectedPersistedVehicle = fileToBean(expectedPersistedVehicleFullPath, Vehicle.class);
        verify(vehicleRepository, times(1)).save(expectedPersistedVehicle);
    }

    /**
     * WHEN There is a software for code
     * AND There is a vehicle for VIN
     * THEN Update the vehicle and software
     */
    @Test
    public void when_ThereIsSoftwareForCode_And_ThereIsVehicleForVIN_Then_UpdateVehicleAndSoftware() {
        //given
        final var mockedSoftwareFullPath = format(TEST_CASES_BASE_PATH, "when-there-is-software-for-code-and-there-is-vehicle-for-vin/mocked-software.json");
        final var mockedSoftware = fileToBean(mockedSoftwareFullPath, Software.class);
        when(softwareRepository.findByCode("GdS6TI")).thenReturn(of(mockedSoftware));

        final var mockedVehicleFullPath = format(TEST_CASES_BASE_PATH, "when-there-is-software-for-code-and-there-is-vehicle-for-vin/mocked-vehicle.json");
        final var mockedVehicle = fileToBean(mockedVehicleFullPath, Vehicle.class);
        when(vehicleRepository.findByVin(mockedVehicle.getVin())).thenReturn(of(mockedVehicle));

        //when
        final var inputSoftwareInformationFullPath = format(TEST_CASES_BASE_PATH, "when-there-is-software-for-code-and-there-is-vehicle-for-vin/input-software-information.json");
        final var inputSoftwareInformation = fileToBean(inputSoftwareInformationFullPath, VehicleSoftware.class);
        underTest.save(inputSoftwareInformation);

        //then
        final var expectedPersistedVehicleFullPath = format(TEST_CASES_BASE_PATH, "when-there-is-software-for-code-and-there-is-vehicle-for-vin/expected-persisted-vehicle.json");
        final var expectedPersistedVehicle = fileToBean(expectedPersistedVehicleFullPath, Vehicle.class);
        verify(vehicleRepository, times(1)).save(expectedPersistedVehicle);
    }
}
