package org.authen.web.exception;

public class InvalidOldPasswordException extends RuntimeException {
	public InvalidOldPasswordException(String message) {
		super(message);
	}
}
