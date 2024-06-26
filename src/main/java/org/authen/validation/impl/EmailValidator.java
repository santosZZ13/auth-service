package org.authen.validation.impl;

import org.authen.validation.ValidEmail;
import org.jetbrains.annotations.NotNull;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.Objects;

public class EmailValidator implements ConstraintValidator<ValidEmail, String> {
	@Override
	public void initialize(ValidEmail constraintAnnotation) {
		ConstraintValidator.super.initialize(constraintAnnotation);
	}

	/**
	 * This regular expression is used to validate email addresses. Here's a breakdown of what each part does:
	 * - `^`: This asserts the start of a line.
	 * - `[a-zA-Z0-9._%+-]+`: This matches one or more (`+`) of the characters in the set. The set includes lowercase letters (`a-z`), uppercase letters (`A-Z`), digits (`0-9`), dot (`.`), underscore (`_`), percent (`%`), plus (`+`), and hyphen (`-`).
	 * - `@` : This matches the `@` symbol.
	 * - `[a-zA-Z0-9.-]+` : This matches one or more (`+`) of the characters in the set. The set includes lowercase letters (`a-z`), uppercase letters (`A-Z`), digits (`0-9`), dot (`.`), and hyphen (`-`).
	 * - `\\.`: This matches the dot (`.`) symbol. The dot needs to be escaped with two backslashes because it's a special character in regular expressions.
	 * - `[a-zA-Z]{2,6}`: This matches between 2 and 6 of the characters in the set. The set includes lowercase letters (`a-z`) and uppercase letters (`A-Z`). This is typically used to match the domain extension (like `.com` or `.org`).
	 * - `$`: This asserts the end of a line.
	 * So, in summary, this regular expression matches a string that starts with one or more alphanumeric characters, dots, underscores, percents, pluses, or hyphens, followed by an `@` symbol, followed by one or more alphanumeric characters, dots, or hyphens, followed by a dot, followed by between 2 and 6 letters, and then the string must end. This pattern matches typical email addresses.
	 * @param value the email to be validated
	 * @return true if the email is valid, false otherwise
	 */
	@Override
	public boolean isValid( String value, ConstraintValidatorContext context) {
		return Objects.nonNull(value) && value.matches("^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,6}$");

	}
}
