package org.authen.web.exception;

import org.authen.errors.api.ApiException;

public class RegisterException extends ApiException {
	public RegisterException(String message, String code, String shortDesc) {
		super(message, code, shortDesc);
	}

	public RegisterException(String message, String code, String shortDesc, Throwable cause) {
		super(message, code, shortDesc, cause);
	}
}
