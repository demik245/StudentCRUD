package com.demik.tocsv.validation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class EmailSufixConstraintValidator implements ConstraintValidator<EmailSufix, String> {

    public String emailSufix;

    @Override
    public void initialize(EmailSufix theEmailSufix) {
        emailSufix = theEmailSufix.value();
    }

    @Override
    public boolean isValid(String theCode, ConstraintValidatorContext theConstraintValidatorContext) {

        boolean result;

        if (theCode != null) {
            result = theCode.endsWith(emailSufix);
        } else {
            result = true;
        }

        return result;
    }
}
