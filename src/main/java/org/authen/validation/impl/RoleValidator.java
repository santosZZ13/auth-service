package org.authen.validation.impl;

import org.authen.enums.RoleConstants;
import org.authen.validation.ValidRole;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.Arrays;
import java.util.Objects;

public class RoleValidator implements ConstraintValidator<ValidRole, String> {
	@Override
	public boolean isValid(String value, ConstraintValidatorContext context) {
		return Objects.nonNull(value) && Arrays.asList(RoleConstants.getRoles()).contains(value);
	}

	@Override
	public void initialize(ValidRole constraintAnnotation) {
		ConstraintValidator.super.initialize(constraintAnnotation);
	}
}
