package com.custodio.vehiclefeature.usecase;

import com.custodio.vehiclefeature.usecase.model.VehicleHardware;
import com.fasterxml.jackson.dataformat.csv.CsvMapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Spy;
import org.mockito.junit.MockitoJUnitRunner;

import static com.custodio.vehiclefeature.util.JSONUtil.fileToBean;
import static java.lang.String.format;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThrows;

@RunWith(MockitoJUnitRunner.class)
public class ReadHardwareInformationTest {
    private static final String TEST_CASES_BASE_PATH = "/test-case/unit/use-case/find-hardware-information/%s";

    @Spy
    private CsvMapper mapper;
    @InjectMocks
    private ReadHardwareInformation underTest;

    /**
     * WHEN Source has valid format
     * THEN Return hardware information from source.
     */
    @Test
    public void when_SourceHasValidFormat_Then_ReturnHardwareInformation() {
        //when
        final var actualHardware = underTest.read("3C3CFFER4ET929645,FhFXVE");
        //then
        final var expectedHardwareFullPath = format(TEST_CASES_BASE_PATH, "when-source-is-valid-format/expected-hardware.json");
        final var expectedHardware = fileToBean(expectedHardwareFullPath, VehicleHardware.class);
        assertEquals(expectedHardware, actualHardware);
    }

    /**
     * WHEN Source has not valid format
     * THEN Throw exception while processing string.
     */
    @Test
    public void when_SourceHasInvalidFormat_Then_ThrowException() {
        //then
        assertThrows(IllegalArgumentException.class, () -> {
            //when
            underTest.read("FhFXVE,,,");
        });
    }

    /**
     * WHEN Source is null
     * THEN Throw exception while processing string.
     */
    @Test
    public void when_SourceIsNull_Then_ThrowException() {
        //then
        assertThrows("The CSV row is mandatory for the hardware data extraction.", NullPointerException.class, () -> {
            //when
            underTest.read(null);
        });
    }
}
