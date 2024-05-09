package org.authen.util.exceptionHandler;

import org.authen.errors.api.ApiException;
import org.authen.util.error.FieldErrorWrapper;
import org.authen.util.error.ResponseError;
import org.authen.wapper.model.GenericResponseErrorWrapper;
import org.authen.wapper.model.GenericResponseSuccessWrapper;
import org.authen.web.exception.RegisterException;
import org.jetbrains.annotations.NotNull;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@ControllerAdvice
public class CustomRestExceptionHandler extends ResponseEntityExceptionHandler {

	@ExceptionHandler({ Exception.class })
	public ResponseEntity<Object> handAll(@NotNull Exception ex, WebRequest request) {
		GenericResponseErrorWrapper genericResponseErrorWrapper = new GenericResponseErrorWrapper(HttpStatus.INTERNAL_SERVER_ERROR, ex.getMessage());
		return new ResponseEntity<>(genericResponseErrorWrapper, new HttpHeaders(), genericResponseErrorWrapper.getStatus());
	}

	@ExceptionHandler({ApiException.class, RegisterException.class})
	public ResponseEntity<Object> handlerApiException(@NotNull ApiException ex, WebRequest request) {
		final String code = ex.getCode();
		final String shortDesc = ex.getShortDesc();
		final String message = ex.getMessage();
		ResponseError responseError = ResponseError.builder()
				.code(code)
				.shortDesc(shortDesc)
				.message(message)
				.build();
		return new ResponseEntity<>(GenericResponseSuccessWrapper.builder()
				.success(Boolean.FALSE)
				.data(responseError)
				.build(), new HttpHeaders(), HttpStatus.BAD_REQUEST);
	}
//	@ExceptionHandler({RegisterException.class})
//	public ResponseEntity<Object> handCustomException(@NotNull Exception ex, WebRequest request) {
//		String message = ex.getMessage();
//		ErrorCode errorCode = JsonConverter.convertJsonToObject(message, ErrorCode.class);
//		List<ErrorField> errorFields = errorCode.getErrorFields();
//		ApiError apiError = new ApiError(HttpStatus.BAD_REQUEST,"Error occurred", null);
//		apiError.setErrors(errorFields);
//		return new ResponseEntity<>(
//				apiError, new HttpHeaders(), apiError.getStatus());
//	}


//	@ExceptionHandler({RuntimeException.class})
//	public ResponseEntity<Object> handRuntimeException(@NotNull RuntimeException ex, WebRequest request) {
//		ApiError apiError = new ApiError(HttpStatus.INTERNAL_SERVER_ERROR, ex.getMessage(), "error occurred");
//		return new ResponseEntity<>(apiError, new HttpHeaders(), apiError.getStatus());
//	}



//	@ResponseStatus(HttpStatus.BAD_REQUEST)
//	@ExceptionHandler(MethodArgumentNotValidException.class)
//	public Map<String, String> handleValidationExceptions(MethodArgumentNotValidException ex) {
//		Map<String, String> errors = new HashMap<>();
//		ex.getBindingResult().getAllErrors().forEach((error) -> {
//			String fieldName = ((FieldError) error).getField();
//			String errorMessage = error.getDefaultMessage();
//			errors.put(fieldName, errorMessage);
//		});
//		return errors;
//	}

	@Override
	protected @NotNull ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers,
																		   HttpStatus status, WebRequest request) {
		BindingResult bindingResult = ex.getBindingResult();
		List<FieldError> fieldErrors = bindingResult.getFieldErrors();
		List<FieldErrorWrapper> fieldErrorWrappers = new ArrayList<>();

		fieldErrors.forEach(fieldError -> {
			FieldErrorWrapper fieldErrorWrapper = new FieldErrorWrapper();
			String errorCode = getErrorCode(fieldError.getArguments());
			fieldErrorWrapper.setErrorCode(errorCode);
			fieldErrorWrapper.setField(fieldError.getField());
			fieldErrorWrapper.setMessage(fieldError.getDefaultMessage());
			fieldErrorWrappers.add(fieldErrorWrapper);
		});

		return ResponseEntity.badRequest().body(GenericResponseErrorWrapper
				.builder()
				.errors(fieldErrorWrappers)
				.message("Validation failed")
				.status(HttpStatus.BAD_REQUEST)
				.build());
	}

	private String getErrorCode(Object[] arguments) {
		if (Objects.nonNull(arguments) && arguments.length > 0) {
			return arguments[1].toString();
		}
		return null;
	}
}


