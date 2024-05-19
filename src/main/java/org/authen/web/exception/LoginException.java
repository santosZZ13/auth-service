package org.authen.web.exception;

import org.authen.exception.ApiException;

public class LoginException extends ApiException {
	public LoginException(String message, String code, String shortDesc) {
		super(message, code, shortDesc);
	}

	public LoginException(String message, String code, String shortDesc, Throwable cause) {
		super(message, code, shortDesc, cause);
	}
}
