package org.authen.util.error;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ErrorField {
	private String errorCode;
	private String field;
	private String message;
}
