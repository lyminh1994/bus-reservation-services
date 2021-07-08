package vn.com.minhlq.validation;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import lombok.AllArgsConstructor;
import vn.com.minhlq.repository.UserRepository;

@AllArgsConstructor
public class DuplicatedUsernameValidator implements ConstraintValidator<DuplicatedUsernameConstraint, String> {

    private UserRepository userRepository;

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        return (value == null || value.isEmpty()) || !userRepository.findByUsername(value).isPresent();
    }

}
