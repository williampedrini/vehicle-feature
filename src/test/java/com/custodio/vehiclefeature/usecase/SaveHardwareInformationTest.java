package com.custodio.vehiclefeature.usecase;

import com.custodio.vehiclefeature.domain.entity.Hardware;
import com.custodio.vehiclefeature.domain.entity.Vehicle;
import com.custodio.vehiclefeature.domain.port.HardwareRepository;
import com.custodio.vehiclefeature.domain.port.VehicleRepository;
import com.custodio.vehiclefeature.usecase.model.VehicleHardware;
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
public class SaveHardwareInformationTest {
    private static final String TEST_CASES_BASE_PATH = "/test-case/unit/use-case/save-hardware-information/%s";

    @Mock
    private VehicleRepository vehicleRepository;
    @Mock
    private HardwareRepository hardwareRepository;
    @InjectMocks
    private SaveHardwareInformation underTest;

    /**
     * WHEN There is not any hardware for code
     * AND There is not any vehicle for VIN
     * THEN persist a new version of the vehicle and hardware.
     */
    @Test
    public void when_ThereIsNotAnyHardwareForCode_And_ThereIsNotAnyVehicleForVIN_Then_PersistsNewVehicleAndHardware() {
        //given
        when(hardwareRepository.findByCode("GdS6TI")).thenReturn(empty());
        when(vehicleRepository.findByVin("WAUHFBFL1DN549274")).thenReturn(empty());

        //when
        final var inputHardwareInformationFullPath = format(TEST_CASES_BASE_PATH, "when-there-is-not-any-hardware-for-code-and-there-is-not-any-vehicle-for-vin/input-hardware-information.json");
        final var inputHardwareInformation = fileToBean(inputHardwareInformationFullPath, VehicleHardware.class);
        underTest.save(inputHardwareInformation);

        //then
        final var expectedPersistedVehicleFullPath = format(TEST_CASES_BASE_PATH, "when-there-is-not-any-hardware-for-code-and-there-is-not-any-vehicle-for-vin/expected-persisted-vehicle.json");
        final var expectedPersistedVehicle = fileToBean(expectedPersistedVehicleFullPath, Vehicle.class);
        verify(vehicleRepository, times(1)).save(expectedPersistedVehicle);
    }

    /**
     * WHEN There is not any hardware for code
     * AND There is a vehicle for VIN
     * THEN Update the existing vehicle
     * AND Create a new version of the hardware
     */
    @Test
    public void when_ThereIsNotAnyHardwareForCode_And_ThereIsVehicleForVIN_Then_UpdateNewVehicleAndCreateNewHardware() {
        //given
        when(hardwareRepository.findByCode("GdS6TI")).thenReturn(empty());

        final var mockedVehicleFullPath = format(TEST_CASES_BASE_PATH, "when-there-is-not-any-hardware-for-code-and-there-is-vehicle-for-vin/mocked-vehicle.json");
        final var mockedVehicle = fileToBean(mockedVehicleFullPath, Vehicle.class);
        when(vehicleRepository.findByVin(mockedVehicle.getVin())).thenReturn(of(mockedVehicle));

        //when
        final var inputHardwareInformationFullPath = format(TEST_CASES_BASE_PATH, "when-there-is-not-any-hardware-for-code-and-there-is-vehicle-for-vin/input-hardware-information.json");
        final var inputHardwareInformation = fileToBean(inputHardwareInformationFullPath, VehicleHardware.class);
        underTest.save(inputHardwareInformation);

        //then
        final var expectedPersistedVehicleFullPath = format(TEST_CASES_BASE_PATH, "when-there-is-not-any-hardware-for-code-and-there-is-vehicle-for-vin/expected-persisted-vehicle.json");
        final var expectedPersistedVehicle = fileToBean(expectedPersistedVehicleFullPath, Vehicle.class);
        verify(vehicleRepository, times(1)).save(expectedPersistedVehicle);
    }

    /**
     * WHEN There is a hardware for code
     * AND There is a vehicle for VIN
     * THEN Update the vehicle and hardware
     */
    @Test
    public void when_ThereIsHardwareForCode_And_ThereIsVehicleForVIN_Then_UpdateVehicleAndHardware() {
        //given
        final var mockedHardwareFullPath = format(TEST_CASES_BASE_PATH, "when-there-is-hardware-for-code-and-there-is-vehicle-for-vin/mocked-hardware.json");
        final var mockedHardware = fileToBean(mockedHardwareFullPath, Hardware.class);
        when(hardwareRepository.findByCode("GdS6TI")).thenReturn(of(mockedHardware));

        final var mockedVehicleFullPath = format(TEST_CASES_BASE_PATH, "when-there-is-hardware-for-code-and-there-is-vehicle-for-vin/mocked-vehicle.json");
        final var mockedVehicle = fileToBean(mockedVehicleFullPath, Vehicle.class);
        when(vehicleRepository.findByVin(mockedVehicle.getVin())).thenReturn(of(mockedVehicle));

        //when
        final var inputHardwareInformationFullPath = format(TEST_CASES_BASE_PATH, "when-there-is-hardware-for-code-and-there-is-vehicle-for-vin/input-hardware-information.json");
        final var inputHardwareInformation = fileToBean(inputHardwareInformationFullPath, VehicleHardware.class);
        underTest.save(inputHardwareInformation);

        //then
        final var expectedPersistedVehicleFullPath = format(TEST_CASES_BASE_PATH, "when-there-is-hardware-for-code-and-there-is-vehicle-for-vin/expected-persisted-vehicle.json");
        final var expectedPersistedVehicle = fileToBean(expectedPersistedVehicleFullPath, Vehicle.class);
        verify(vehicleRepository, times(1)).save(expectedPersistedVehicle);
    }
}
