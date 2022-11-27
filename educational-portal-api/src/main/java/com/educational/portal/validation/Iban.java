package com.educational.portal.validation;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Documented
@Constraint(validatedBy = IbanValidation.class)
@Target( { ElementType.METHOD, ElementType.FIELD })
@Retention(value = RetentionPolicy.RUNTIME)
public @interface Iban {

    String message() default "Your bank id is not valid";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
