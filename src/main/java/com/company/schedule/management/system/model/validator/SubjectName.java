package com.company.schedule.management.system.model.validator;

import com.company.schedule.management.system.model.validator.impl.SubjectNameValidator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = SubjectNameValidator.class)
@Documented
public @interface SubjectName {

    String message() default "Must begin with a capital letter and contain only letters, example: \"Math\"";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
