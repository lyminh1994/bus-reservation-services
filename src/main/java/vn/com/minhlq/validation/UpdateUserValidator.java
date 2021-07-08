package vn.com.minhlq.validation;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import lombok.AllArgsConstructor;
import vn.com.minhlq.dto.UpdateUserCommand;
import vn.com.minhlq.model.User;
import vn.com.minhlq.repository.UserRepository;

@AllArgsConstructor
public class UpdateUserValidator implements ConstraintValidator<UpdateUserConstraint, UpdateUserCommand> {

    private UserRepository userRepository;

    @Override
    public boolean isValid(UpdateUserCommand value, ConstraintValidatorContext context) {
        String inputEmail = value.getParam().getEmail();
        String inputUsername = value.getParam().getUsername();
        final User targetUser = value.getTargetUser();

        boolean isEmailValid = userRepository.findByEmail(inputEmail).map(user -> user.equals(targetUser)).orElse(true);
        boolean isUsernameValid = userRepository.findByUsername(inputUsername).map(user -> user.equals(targetUser))
                .orElse(true);
        if (isEmailValid && isUsernameValid) {
            return true;
        } else {
            context.disableDefaultConstraintViolation();
            if (!isEmailValid) {
                context.buildConstraintViolationWithTemplate("email already exist").addPropertyNode("email")
                        .addConstraintViolation();
            }
            if (!isUsernameValid) {
                context.buildConstraintViolationWithTemplate("username already exist").addPropertyNode("username")
                        .addConstraintViolation();
            }
            return false;
        }
    }

}
