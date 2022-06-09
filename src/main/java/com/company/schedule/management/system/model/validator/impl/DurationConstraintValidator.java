package com.company.schedule.management.system.model.validator.impl;

import com.company.schedule.management.system.model.validator.DurationConstraint;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.time.Duration;

public class DurationConstraintValidator implements ConstraintValidator<DurationConstraint, Duration> {

    @Override
    public boolean isValid(Duration value, ConstraintValidatorContext context) {
        if (value == null) return false;
        long durationInMinutes = value.toMinutes();
        return durationInMinutes == 90;
    }
}
