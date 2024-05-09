package org.authen.web.exception;

import org.authen.errors.api.ApiException;

public class RegisterEventException extends ApiException {
	public RegisterEventException(String message, String code, String shortDesc) {
		super(message, code, shortDesc);
	}

	public RegisterEventException(String code, String shortDesc, String message, Throwable cause) {
		super(code, shortDesc, message, cause);
	}
}
