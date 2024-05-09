package org.authen.validation.impl;

import org.authen.validation.ValidUserName;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.Objects;

public class UserNameValidator implements ConstraintValidator<ValidUserName, String> {
	@Override
	public void initialize(ValidUserName constraintAnnotation) {
		ConstraintValidator.super.initialize(constraintAnnotation);
	}

	/**
	 * Up to 20 characters
	 * Except: <, >, [,], ", :, space
	 * @param value object to validate
	 * @param context context in which the constraint is evaluated
	 * @return true if the username is valid, false otherwise
	 */
	@Override
	public boolean isValid(String value, ConstraintValidatorContext context) {
		return Objects.nonNull(value) && value.matches("^[^<>\\[\\] :\"]{1,20}$");
	}
}
