package org.authen.validation.impl;

import org.authen.validation.ValidNormalField;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class NormalFieldValidator implements ConstraintValidator<ValidNormalField, String> {
	@Override
	public void initialize(ValidNormalField constraintAnnotation) {
		ConstraintValidator.super.initialize(constraintAnnotation);
	}

	/**
	 * Up to 30 characters
	 * Except <, >, [, ]
	 * @param value the field to be validated
	 * @return true if the field is valid, false otherwise
	 */
	@Override
	public boolean isValid(String value, ConstraintValidatorContext context) {
		return value.matches("^[^<>\\[\\]]{1,30}$");
	}
}
