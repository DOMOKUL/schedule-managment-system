package com.company.schedule.management.system.model.validator.impl;

import com.company.schedule.management.system.model.validator.PersonName;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class PersonNameValidator implements ConstraintValidator<PersonName, String> {

    @Override
    public boolean isValid(String value, ConstraintValidatorContext constraintValidatorContext) {
        if (value == null) return false;

        return value.chars().noneMatch(Character::isSpaceChar) &&
                value.chars().allMatch(Character::isLetter) &&
                value.substring(1).chars().allMatch(Character::isLowerCase) &&
                Character.isUpperCase(value.charAt(0));
    }
}
