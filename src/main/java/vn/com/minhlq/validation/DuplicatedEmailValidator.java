package vn.com.minhlq.validation;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import lombok.AllArgsConstructor;
import vn.com.minhlq.repository.UserRepository;

@AllArgsConstructor
public class DuplicatedEmailValidator implements ConstraintValidator<DuplicatedEmailConstraint, String> {

    private UserRepository userRepository;

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        return (value == null || value.isEmpty()) || !userRepository.findByEmail(value).isPresent();
    }

}
