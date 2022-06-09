package com.company.schedule.management.system.model.validator;

import com.company.schedule.management.system.model.validator.impl.DurationConstraintValidator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = DurationConstraintValidator.class)
@Documented
public @interface DurationConstraint {

    String message() default "The duration of the class should be 90 minutes.";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
