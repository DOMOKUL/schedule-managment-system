package com.company.schedule.management.system.model.validator.impl;

import com.company.schedule.management.system.model.validator.CurrentDate;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.time.LocalDate;
import java.util.Date;

public class CurrentDateValidator implements ConstraintValidator<CurrentDate, Date> {

    @Override
    public boolean isValid(Date date, ConstraintValidatorContext constraintValidatorContext) {
        if (date == null) return false;
        LocalDate currentDate = LocalDate.now();
        LocalDate dateOfValue = LocalDate.parse(date.toString());
        return currentDate.getYear() == dateOfValue.getYear();
    }
}
