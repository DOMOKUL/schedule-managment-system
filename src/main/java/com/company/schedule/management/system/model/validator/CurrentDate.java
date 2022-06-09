package com.company.schedule.management.system.model.validator;

import com.company.schedule.management.system.model.validator.impl.CurrentDateValidator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = CurrentDateValidator.class)
@Documented
public @interface CurrentDate {

    String message() default "Should use the current university year";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
