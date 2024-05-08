package org.authen.validation;

import lombok.extern.log4j.Log4j2;

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
