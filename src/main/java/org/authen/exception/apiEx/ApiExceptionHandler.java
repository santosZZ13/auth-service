package org.authen.exception.apiEx;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ApiExceptionHandler {

	@ExceptionHandler(Exception.class)
	@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
	public ResponseEntity<ResponseModel> handleException(Exception ex) {
		ResponseModel responseModel = ResponseModel
				.builder()
				.success(Boolean.FALSE)
				.data(ResponseModel.Data
						.builder()
						.name(ex.getClass().getSimpleName())
						.message(ex.getMessage())
						.code(ErrorCode.INTERNAL_SERVER_ERROR)
						.status(HttpStatus.INTERNAL_SERVER_ERROR.value())
						.build())
				.build();
		return ResponseEntity
				.status(HttpStatus.INTERNAL_SERVER_ERROR)
				.body(responseModel);
	}
}
