package org.authen.web.exception;

import org.authen.exception.ApiException;

public class VerifyException extends ApiException {
	public VerifyException(String message, String code, String shortDesc) {
		super(message, code, shortDesc);
	}

	public VerifyException(String code, String shortDesc, String message, Throwable cause) {
		super(code, shortDesc, message, cause);
	}
}
