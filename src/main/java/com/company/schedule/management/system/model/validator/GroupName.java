package com.company.schedule.management.system.model.validator;

import com.company.schedule.management.system.model.validator.impl.GroupNameValidator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = GroupNameValidator.class)
@Documented
public @interface GroupName {

    String message() default "Must contain 4 uppercase letters and 4 digits, example: \"BSBO-04-19\"";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
