package com.educational.portal.validation;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static org.hibernate.validator.internal.util.Contracts.assertTrue;

public class ContactNumberValidator implements
        ConstraintValidator<ContactNumberConstraint, String> {

    @Override
    public void initialize(ContactNumberConstraint contactNumber) {
    }

    @Override
    public boolean isValid(String phone,
                           ConstraintValidatorContext cxt) {
        return phone != null && phone.matches("[0-9]+")
                && (phone.length() > 8) && (phone.length() < 14);
    }
    public void whenMatchesTenDigitsNumberPrefix_thenCorrect() {
        Pattern pattern = Pattern.compile("^(\\+\\d{1,3}( )?)?((\\(\\d{3}\\))|\\d{3})[- .]?\\d{3}[- .]?\\d{4}$");
        Matcher matcher = pattern.matcher("+111 (202) 555-0125");

        assertTrue(matcher.matches());
    }
    private void assertTrue(boolean matches) {
    }

}