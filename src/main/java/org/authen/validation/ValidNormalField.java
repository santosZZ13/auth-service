package org.authen.validation;

import org.authen.validation.impl.NormalFieldValidator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.*;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Target({TYPE, FIELD, ANNOTATION_TYPE})
@Retention(RUNTIME)
@Constraint(validatedBy = NormalFieldValidator.class)
@Documented
public @interface ValidNormalField {
	String message() default "";
	String code() default "";
	Class<?>[] groups() default {};
	Class<? extends Payload>[] payload() default {};
}
