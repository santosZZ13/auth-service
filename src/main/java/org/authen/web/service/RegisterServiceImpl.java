package org.authen.web.service;

import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.authen.wapper.model.GenericResponseSuccessWrapper;
import org.authen.web.exception.RegisterException;
import org.authen.listener.OnRegistrationCompleteEvent;
import org.authen.service.user.UserService;
import org.authen.util.error.ErrorCode;
import org.authen.util.converter.JsonConverter;
import org.authen.util.validate.ValidateField;
import org.authen.web.dto.register.ConfirmRegistrationResponse;
import org.authen.web.dto.register.RegisterConfig;
import org.authen.web.dto.register.RegisterDTORequest;
import org.authen.web.dto.register.RegisterDTOResponse;
import org.jetbrains.annotations.Contract;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.authen.level.service.model.UserModel;
import org.authen.level.service.model.VerificationTokenModel;
import org.authen.level.service.verificationToken.VerificationTokenLogicServiceImpl;

import javax.servlet.http.HttpServletRequest;
import java.util.Calendar;
import java.util.Locale;
import java.util.Map;
import java.util.Objects;

import static org.authen.web.dto.register.RegisterDTORequest.*;
import static org.authen.web.dto.register.RegisterDTORequest.ROLE;

@Service
@Log4j2
@AllArgsConstructor
public class RegisterServiceImpl implements RegisterService {

	private final ApplicationEventPublisher eventPublisher;
	private final PasswordEncoder passwordEncoder;
	private final UserService userService;
	private final VerificationTokenLogicServiceImpl verificationTokenLogicService;


	private final String MISSING_REQUIRED_FIELD_CODE = "C1010003";
	private final String MISSING_REQUIRED_FIELD_MESSAGE = "Mandatory parameter {%s} is not specified";

	private final String INVALID_FIELD_CODE = "C1010004";
	private final String INVALID_USERNAME_MESSAGE = "Invalid value specified for parameter {%s}.";
	private final String INVALID_EMAIL_MESSAGE = "Invalid value specified for parameter {%s}.";
	private final String INVALID_FIRST_NAME_MESSAGE = "Invalid value specified for parameter {%s}.";
	private final String INVALID_LAST_NAME_MESSAGE = "Invalid value specified for parameter {%s}.";
	private final String INVALID_ROLE_MESSAGE = "Invalid value specified for parameter {%s}.";

	private final String ALREADY_USER_CODE = "C1040005";
	private final String ALREADY_USER_MESSAGE = "User with {%s}  already exists on the server.";



	@Override
	public ResponseEntity<GenericResponseSuccessWrapper> registerAccount(RegisterDTORequest registerDTORequest) {
		ErrorCode errorCode = new ErrorCode();
		Map<String, String> mapFromRegisterForm = registerDTORequest.toMapFromRegisterForm();

		checkMissingRequiredFields(mapFromRegisterForm, errorCode);
		validateUser(mapFromRegisterForm, errorCode);

//		if (userService.isUsernameExist(mapFromRegisterForm.get(USERNAME))) {1
//			errorCode.addError(ALREADY_USER_CODE, String.format(ALREADY_USER_MESSAGE, USERNAME));
//			errorCode.addErrorField(ALREADY_USER_CODE, USERNAME, String.format(ALREADY_USER_MESSAGE, USERNAME));
//		}

		// In case of no error, save user to DB
		if (Objects.equals(errorCode.errorCount(), 0)) {
			final String hashedPassword = passwordEncoder.encode(mapFromRegisterForm.get(PASSWORD));
			final UserModel userModelWithHashPasswordToSaveInDB = registerDTORequest.toUserEntityWithHashPasswordToSaveInDB(hashedPassword);
			RegisterConfig registerConfig = RegisterConfig.builder()
					.username(registerDTORequest.getRegisterForm().getUsername())
					.password(registerDTORequest.getRegisterForm().getPassword())
					.email(registerDTORequest.getRegisterForm().getEmail())
					.type(registerDTORequest.getRegisterForm().getType())
					.role(registerDTORequest.getRegisterForm().getRole())
					.locate(registerDTORequest.getRegisterForm().getLocate())
					.firstName(registerDTORequest.getRegisterForm().getFirstName())
					.lastName(registerDTORequest.getRegisterForm().getLastName())
					.build();
			RegisterDTOResponse registerResponse = RegisterDTOResponse.builder()
					.registerConfig(registerConfig)
					.build();

//			userService.saveUser(userEntityWithHashPasswordToSaveInDB);

			// Publish event
			try {
//				final Locale locale = LocaleContextHolder.getLocale();
//				final String contextPath = ServletUriComponentsBuilder.fromCurrentContextPath().build().toUriString();
				eventPublisher.publishEvent(new OnRegistrationCompleteEvent(
						userModelWithHashPasswordToSaveInDB,
						null,
						null));
			} catch (Exception e) {
				log.info("Error: {}", e.getMessage());
				throw new RuntimeException(e.getMessage());
			}

			return ResponseEntity.ok().body(GenericResponseSuccessWrapper
					.builder()
					.success(Boolean.TRUE)
					.data(registerResponse)
					.build()
			);
		} else {
			// In case of error, throw exception
			String errorCodeInString = JsonConverter.convertToJsonString(errorCode);
			throw new RegisterException(errorCodeInString);
		}
	}

	@Contract(pure = true)
	private void checkMissingRequiredFields(Map<String, String> fields, ErrorCode errorCode) {
		for (String key : fields.keySet()) {
			if (Objects.isNull(fields.get(key))) {
				errorCode.addError(MISSING_REQUIRED_FIELD_CODE, String.format(MISSING_REQUIRED_FIELD_MESSAGE, key));
				errorCode.addErrorField(MISSING_REQUIRED_FIELD_CODE, key, String.format(MISSING_REQUIRED_FIELD_MESSAGE, key));
			}
		}
		String errorCodeInString = JsonConverter.convertToJsonString(errorCode);
		if (errorCode.errorCount() > 0) {
			throw new RegisterException(errorCodeInString);
		}
	}

	private void validateUser(Map<String, String> mapFromRegisterForm, ErrorCode errorCode) {
		String requestUserName = mapFromRegisterForm.get(USERNAME);
		String requestEmail = mapFromRegisterForm.get(EMAIL);
		String requestFirstName = mapFromRegisterForm.get(FIRST_NAME);
		String requestLastName = mapFromRegisterForm.get(LAST_NAME);
		String requestRole = mapFromRegisterForm.get(ROLE);

		if (!ValidateField.checkValidUserName(requestUserName)) {
			errorCode.addError(INVALID_FIELD_CODE, String.format(INVALID_USERNAME_MESSAGE, USERNAME));
			errorCode.addErrorField(INVALID_FIELD_CODE, USERNAME, String.format(INVALID_USERNAME_MESSAGE, USERNAME));
		}

		if (!ValidateField.checkValidEmail(requestEmail)) {
			errorCode.addError(INVALID_FIELD_CODE, String.format(INVALID_EMAIL_MESSAGE, EMAIL));
			errorCode.addErrorField(INVALID_FIELD_CODE, EMAIL, String.format(INVALID_EMAIL_MESSAGE, EMAIL));
		}

		if (!ValidateField.checkNormalUser(requestFirstName)) {
			errorCode.addError(INVALID_FIELD_CODE, String.format(INVALID_FIRST_NAME_MESSAGE, FIRST_NAME));
			errorCode.addErrorField(INVALID_FIELD_CODE, FIRST_NAME, String.format(INVALID_FIRST_NAME_MESSAGE, FIRST_NAME));
		}

		if (!ValidateField.checkNormalUser(requestLastName)) {
			errorCode.addError(INVALID_FIELD_CODE, String.format(INVALID_LAST_NAME_MESSAGE, LAST_NAME));
			errorCode.addErrorField(INVALID_FIELD_CODE, LAST_NAME, String.format(INVALID_LAST_NAME_MESSAGE, LAST_NAME));
		}

		if (!ValidateField.isExistRole(requestRole)) {
			errorCode.addError(INVALID_FIELD_CODE, String.format(INVALID_ROLE_MESSAGE, ROLE));
			errorCode.addErrorField(INVALID_FIELD_CODE, ROLE, String.format(INVALID_ROLE_MESSAGE, ROLE));
		}

		//TODO: Validate password

		if (!Objects.equals(errorCode.errorCount(), 0)) {
			String errorCodeInString = JsonConverter.convertToJsonString(errorCode);
			throw new RegisterException(errorCodeInString);
		}
	}


	@Override
	public ResponseEntity<GenericResponseSuccessWrapper> confirmRegistration(String token, HttpServletRequest request) {
//		VerificationToken verificationToken = userService.getVerificationToken(token);
		VerificationTokenModel verificationTokenModel = verificationTokenLogicService.getVerificationToken(token);
		Locale locale = request.getLocale();

		if (Objects.isNull(verificationTokenModel)) {
//			String message = messages.getMessage("auth.message.invalidToken", null, locale);
			throw new RuntimeException("redirect:/badUser.html?lang=" + locale.getLanguage());
		}

		UserModel userModel = verificationTokenModel.getUserModel();
		Calendar cal = Calendar.getInstance();

		if ((verificationTokenModel.getExpiryDate().getTime() - cal.getTime().getTime()) <= 0) {
//			String messageValue = messages.getMessage("auth.message.expired", null, locale)
			throw new RuntimeException("redirect:/badUser.html?lang=" + locale.getLanguage());
		}

		userService.updateEnabledByUsername(Boolean.TRUE, userModel.getUsername());

		ConfirmRegistrationResponse confirmRegistrationResponse = ConfirmRegistrationResponse.builder()
				.message(String.format("User %s has been activated", userModel.getUsername()))
				.data("redirect:/login.html?lang=" + request.getLocale().getLanguage())
				.build();

		return ResponseEntity.ok().body(GenericResponseSuccessWrapper
				.builder()
				.success(Boolean.TRUE)
				.data(confirmRegistrationResponse)
				.build()
		);
	}
}
