package com.lib.mgmt.validators;

import javax.validation.Constraint;
import javax.validation.Payload;
import javax.validation.constraints.NotNull;
import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = EnumValidatorConstraint.class)
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@NotNull
public @interface DurationValidator {

    String message() default "Duration Must be following types DAY, WEEK, MONTH, YEAR";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
    Class<? extends Enum> enumClass();
}
