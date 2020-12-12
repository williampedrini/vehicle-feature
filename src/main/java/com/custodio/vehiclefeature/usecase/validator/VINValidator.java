package com.custodio.vehiclefeature.usecase.validator;

import org.jetbrains.annotations.Nullable;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static java.util.Optional.ofNullable;
import static java.util.regex.Pattern.compile;

@Component
class VINValidator implements ConstraintValidator<VIN, String> {
    private final Pattern pattern;

    VINValidator(@Value("${application.vin-pattern}") final String pattern) {
        this.pattern = compile(pattern);
    }

    @Override
    public boolean isValid(@Nullable final String vin, final ConstraintValidatorContext constraintValidatorContext) {
        return ofNullable(vin)
                       .map(pattern::matcher)
                       .filter(Matcher::matches)
                       .isPresent();
    }
}
