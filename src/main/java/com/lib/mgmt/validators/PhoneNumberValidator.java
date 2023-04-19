package com.lib.mgmt.validators;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class PhoneNumberValidator implements ConstraintValidator<PhoneNumber, Long> {
    @Override
    public boolean isValid(Long phoneNumber, ConstraintValidatorContext constraintValidatorContext) {
        //Pattern ptrn = Pattern.compile("(0/91)?[7-9][0-9]{9}");
        //Matcher match = ptrn.matcher(str);
        //return (match.find() && match.group().equals(str));

        return phoneNumber != null ;
    }
}
