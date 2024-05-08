package org.authen.validation.impl;


import org.authen.validation.ValidField;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.Objects;

public class RequireFieldValidator implements ConstraintValidator<ValidField, String> {
	@Override
	public void initialize(ValidField constraintAnnotation) {
		ConstraintValidator.super.initialize(constraintAnnotation);
	}

	@Override
	public boolean isValid(String value, ConstraintValidatorContext context) {
		return !Objects.isNull(value) &&
				!value.isEmpty() &&
				!value.isBlank();
	}
}
