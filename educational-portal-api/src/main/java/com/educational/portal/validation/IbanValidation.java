package com.educational.portal.validation;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class IbanValidation implements ConstraintValidator<Iban, String> {

    @Override
    public void initialize(Iban iban) {
    }

    @Override
    public boolean isValid(String iban, ConstraintValidatorContext ctx) {
        if (iban.startsWith("PL")) {
            String ibanNumbers = iban.substring(2);
            return ibanNumbers.chars().allMatch( Character::isDigit ) && ibanNumbers.chars().count()== 26;
        }
        return false;
    }
}

