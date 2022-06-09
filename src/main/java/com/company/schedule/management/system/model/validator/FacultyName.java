package com.company.schedule.management.system.model.validator;

import com.company.schedule.management.system.model.validator.impl.FacultyNameValidator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = FacultyNameValidator.class)
@Documented
public @interface FacultyName {

    String message() default "Shouldn't contains digits and spaces, all letters must be capital, example: \"IKBSP\"";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
