package vn.com.minhlq.validation;

import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Retention;

import javax.validation.Constraint;
import javax.validation.Payload;

@Constraint(validatedBy = DuplicatedUsernameValidator.class)
@Retention(RUNTIME)
public @interface DuplicatedUsernameConstraint {

    String message() default "duplicated username";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

}
