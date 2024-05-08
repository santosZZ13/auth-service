package org.authen.validation;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class RequireFieldValidator implements ConstraintValidator<RequireField, String> {

	@Override
	public void initialize(RequireField constraintAnnotation) {
		ConstraintValidator.super.initialize(constraintAnnotation);
	}

	@Override
	public boolean isValid(String value, ConstraintValidatorContext context) {
		return false;
	}
}
