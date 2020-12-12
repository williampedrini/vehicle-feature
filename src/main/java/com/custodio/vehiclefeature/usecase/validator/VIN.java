package com.custodio.vehiclefeature.usecase.validator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.ElementType.PARAMETER;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Target({METHOD, FIELD, PARAMETER})
@Retention(RUNTIME)
@Constraint(validatedBy = VINValidator.class)
public @interface VIN {
    String message() default "Invalid format for VIN code.";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}