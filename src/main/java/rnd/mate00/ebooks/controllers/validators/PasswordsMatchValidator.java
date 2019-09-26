package rnd.mate00.ebooks.controllers.validators;

import rnd.mate00.ebooks.commands.ReaderCommand;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class PasswordsMatchValidator implements ConstraintValidator<PasswordsMatch, ReaderCommand> {

    @Override
    public void initialize(PasswordsMatch constraintAnnotation) {
    }

    @Override
    public boolean isValid(ReaderCommand value, ConstraintValidatorContext context) {

        return (value.getPassword() != null && value.getRepeatedPassword() != null) &&
                value.getPassword().equals(value.getRepeatedPassword());
    }
}
