package com.custodio.vehiclefeature.usecase.validator;

import org.junit.Test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class VINValidatorTest {
    private static final String VIN_REGEX_PATTERN = "^[A-HJ-NPR-Za-hj-npr-z\\d]{8}[\\dX][A-HJ-NPR-Za-hj-npr-z\\d]{2}\\d{6}$";

    private final VINValidator underTest = new VINValidator(VIN_REGEX_PATTERN);

    /**
     * WHEN Provided VIN has invalid format
     * THEN Return false for validation.
     */
    @Test
    public void when_ProvidedVINHasInvalidFormat_Then_ReturnFalse() {
        //when
        final var isValid = underTest.isValid("invalid", null);
        //then
        assertFalse(isValid);
    }

    /**
     * WHEN Provided VIN is null
     * THEN Return false for validation.
     */
    @Test
    public void when_ProvidedVINIsNull_Then_ReturnFalse() {
        //when
        final var isValid = underTest.isValid(null, null);
        //then
        assertFalse(isValid);
    }

    /**
     * WHEN Provided VIN has valid information
     * THEN Return true for validation.
     */
    @Test
    public void when_ProvidedVINHasValidFormat_Then_ReturnTrue() {
        //when
        final var isValid = underTest.isValid("WAUHFBFL1DN549274", null);
        //then
        assertTrue(isValid);
    }
}