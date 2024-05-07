package org.authen.util.exceptionHandler;

import org.authen.errors.api.ApiException;
import org.authen.util.error.ErrorCode;
import org.authen.util.error.ErrorField;
import org.authen.util.converter.JsonConverter;
import org.authen.util.error.ResponseError;
import org.authen.wapper.model.GenericResponseWrapper;
import org.authen.web.exception.RegisterException;
import org.jetbrains.annotations.NotNull;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.List;

@ControllerAdvice
public class CustomRestExceptionHandler extends ResponseEntityExceptionHandler {

	@ExceptionHandler({ Exception.class })
	public ResponseEntity<Object> handAll(@NotNull Exception ex, WebRequest request) {
		ApiError apiError = new ApiError(HttpStatus.INTERNAL_SERVER_ERROR, ex.getLocalizedMessage(), "error occurred");
		return new ResponseEntity<>(apiError, new HttpHeaders(), apiError.getStatus());
	}


	@ExceptionHandler({RegisterException.class})
	public ResponseEntity<Object> handCustomException(@NotNull Exception ex, WebRequest request) {
		String message = ex.getMessage();
		ErrorCode errorCode = JsonConverter.convertJsonToObject(message, ErrorCode.class);
		List<ErrorField> errorFields = errorCode.getErrorFields();
		ApiError apiError = new ApiError(HttpStatus.BAD_REQUEST,"Error occurred", null);
		apiError.setErrors(errorFields);
		return new ResponseEntity<>(
				apiError, new HttpHeaders(), apiError.getStatus());
	}


	@ExceptionHandler({RuntimeException.class})
	public ResponseEntity<Object> handRuntimeException(@NotNull RuntimeException ex, WebRequest request) {
		ApiError apiError = new ApiError(HttpStatus.INTERNAL_SERVER_ERROR, ex.getMessage(), "error occurred");
		return new ResponseEntity<>(apiError, new HttpHeaders(), apiError.getStatus());
	}

	@ExceptionHandler({ApiException.class})
	public ResponseEntity<Object> handlerApiException(@NotNull ApiException ex, WebRequest request) {
		final String code = ex.getCode();
		final String shortDesc = ex.getShortDesc();
		final String message = ex.getMessage();
		ResponseError responseError = ResponseError.builder()
				.code(code)
				.shortDesc(shortDesc)
				.message(message)
				.build();
		return new ResponseEntity<>(GenericResponseWrapper.builder()
				.success(Boolean.FALSE)
				.data(responseError)
				.build(), new HttpHeaders(), HttpStatus.BAD_REQUEST);
	}
}


