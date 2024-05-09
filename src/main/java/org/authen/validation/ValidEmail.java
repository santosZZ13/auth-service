package org.authen.validation;

import org.authen.validation.impl.EmailValidator;
import org.authen.validation.impl.RequireFieldValidator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.*;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Target({TYPE, FIELD, ANNOTATION_TYPE})
@Retention(RUNTIME)
@Constraint(validatedBy = EmailValidator.class)
@Documented
public @interface ValidEmail {
	String message() default "";
	String code() default "";
	Class<?>[] groups() default {};
	Class<? extends Payload>[] payload() default {};
}
