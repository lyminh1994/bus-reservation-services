package vn.com.minhlq.validation;

import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Retention;

import javax.validation.Constraint;

@Constraint(validatedBy = UpdateUserValidator.class)
@Retention(RUNTIME)
public @interface UpdateUserConstraint {

    String message() default "invalid update param";

    Class<?>[] groups() default {};

    Class<?>[] payload() default {};

}