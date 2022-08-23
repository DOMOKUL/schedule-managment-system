package com.company.schedule.management.system.model.validator.impl;

import com.company.schedule.management.system.model.validator.FacultyName;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class FacultyNameValidator implements ConstraintValidator<FacultyName, String> {

    @Override
    public boolean isValid(String value, ConstraintValidatorContext constraintValidatorContext) {
        if (value == null) {
            return false;
        }
        return value.chars().noneMatch(Character::isSpaceChar) &&
                value.chars().noneMatch(Character::isLowerCase) &&
                value.chars().allMatch(Character::isLetter);
    }
}
