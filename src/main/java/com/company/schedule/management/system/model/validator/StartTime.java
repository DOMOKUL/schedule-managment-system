package com.company.schedule.management.system.model.validator;

import com.company.schedule.management.system.model.validator.impl.StartTimeValidator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = StartTimeValidator.class)
@Documented
public @interface StartTime {

    String message() default "Must contain the start time of the class, but only between 9:00 and 19:30";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
