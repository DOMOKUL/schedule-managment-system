package com.company.schedule.management.system.model.validator;

import com.company.schedule.management.system.model.validator.impl.PersonNameValidator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = PersonNameValidator.class)
@Documented
public @interface PersonName {

    String message() default "Must begin with a capital letter and contain only letters, example: \"Ivan\"";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
