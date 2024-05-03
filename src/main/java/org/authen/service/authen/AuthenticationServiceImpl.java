package org.authen.service.authen;

import lombok.AllArgsConstructor;
import org.authen.dto.register.ConfirmRegistrationResponse;
import org.authen.enums.AuthConstants;
import org.authen.exception.registerEx.RegisterException;
import org.authen.dto.login.LoginRequestDTO;
import org.authen.dto.login.LoginResponseDTO;
import org.authen.dto.register.RegisterConfig;
import org.authen.dto.register.RegisterDTORequest;
import org.authen.dto.logout.LogoutRequestDTO;
import org.authen.dto.register.RegisterDTOResponse;
import org.authen.listener.OnRegistrationCompleteEvent;
import org.authen.persistence.model.UserEntity;
import org.authen.jwt.JwtTokenService;
import org.authen.exception.apiEx.ResponseModel;
import org.authen.persistence.model.VerificationToken;
import org.authen.service.validation.ValidateRegister;
import org.authen.service.user.UserService;
import org.authen.util.*;
import org.jetbrains.annotations.Contract;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import java.util.*;

import static org.authen.dto.register.RegisterDTORequest.*;

@Service
@AllArgsConstructor
//@Transactional(readOnly = false)
public class AuthenticationServiceImpl implements AuthenticationService {

	private static final Logger log = LoggerFactory.getLogger(AuthenticationServiceImpl.class);
	private final UserService userService;
	private final ValidateRegister validateRegister;
	private final PasswordEncoder passwordEncoder;
	private final AuthenticationManager authenticationManager;
	private final JwtTokenService jwtTokenService;
	private final ApplicationEventPublisher eventPublisher;

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
	@Transactional
	public ResponseEntity<ResponseModel> registerAccount(RegisterDTORequest registerDTORequest, HttpServletRequest request) {
		ErrorCode errorCode = new ErrorCode();
		Map<String, String> mapFromRegisterForm = registerDTORequest.toMapFromRegisterForm();
		checkMissingRequiredFields(mapFromRegisterForm, errorCode);
		validateUser(mapFromRegisterForm, errorCode);

		if (userService.isUsernameExist(mapFromRegisterForm.get(USERNAME))) {
			errorCode.addError(ALREADY_USER_CODE, String.format(ALREADY_USER_MESSAGE, USERNAME));
			errorCode.addErrorField(ALREADY_USER_CODE, USERNAME, String.format(ALREADY_USER_MESSAGE, USERNAME));
		}

		// In case of no error, save user to DB
		if (Objects.equals(errorCode.errorCount(), 0)) {
			final String hashedPassword = passwordEncoder.encode(mapFromRegisterForm.get(PASSWORD));
			final UserEntity userEntityWithHashPasswordToSaveInDB = registerDTORequest.toUserEntityWithHashPasswordToSaveInDB(hashedPassword);
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

			try {
				eventPublisher.publishEvent(new OnRegistrationCompleteEvent(
						userEntityWithHashPasswordToSaveInDB,
						request.getLocale(),
						request.getContextPath()));
			} catch (Exception e) {
				log.info("Error: {}", e.getMessage());
				throw new RuntimeException(e.getMessage());
			}

			return ResponseEntity.ok().body(ResponseModel
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

	@Override
	@Transactional(readOnly = false)
	public ResponseEntity<ResponseModel> confirmRegistration(String token, HttpServletRequest request) {
		VerificationToken verificationToken = userService.getVerificationToken(token);
		Locale locale = request.getLocale();

		if (Objects.isNull(verificationToken)) {
//			String message = messages.getMessage("auth.message.invalidToken", null, locale);
			throw new RuntimeException("redirect:/badUser.html?lang=" + locale.getLanguage());
		}

		UserEntity user = verificationToken.getUser();
		Calendar cal = Calendar.getInstance();

		if ((verificationToken.getExpiryDate().getTime() - cal.getTime().getTime()) <= 0) {
//			String messageValue = messages.getMessage("auth.message.expired", null, locale)
			throw new RuntimeException("redirect:/badUser.html?lang=" + locale.getLanguage());
		}

		userService.updateEnabledByUsername(Boolean.TRUE, user.getUsername());

		ConfirmRegistrationResponse confirmRegistrationResponse = ConfirmRegistrationResponse.builder()
				.message(String.format("User %s has been activated", user.getUsername()))
				.data("redirect:/login.html?lang=" + request.getLocale().getLanguage())
				.build();

		return ResponseEntity.ok().body(ResponseModel
				.builder()
				.success(Boolean.TRUE)
				.data(confirmRegistrationResponse)
				.build()
		);
	}



	@Override
	public ResponseEntity<ResponseModel> login(LoginRequestDTO request) {

		String username = request.getUsername();
		String password = request.getPassword();

		UserEntity userEntity = userService.findByUsername(username);
		ResponseModel responseModel = new ResponseModel();


		if (Objects.isNull(userEntity)) {
			responseModel.setSuccess(Boolean.FALSE);
			responseModel.setData("User %s not found" + username);
			return ResponseEntity
					.badRequest()
					.body(responseModel);
		}


		Authentication authenticate;

		try {
			Authentication authentication = new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword(), userService.getAuthorities(List.of(userEntity.getRole())));
			authenticate = authenticationManager.authenticate(authentication);

			if (authenticate.isAuthenticated()) {
				SecurityContextHolder.getContext().setAuthentication(authenticate);
			}


			Authentication authentication1 = SecurityContextHolder.getContext().getAuthentication();
			Map<String, String> tokens = jwtTokenService.generateTokens(authenticate, userEntity);


			return ResponseEntity
					.ok()
					.body(ResponseModel
							.builder()
							.success(Boolean.TRUE)
							.data(LoginResponseDTO
									.builder()
									.accessToken(tokens.get(AuthConstants.ACCESS_TOKEN))
									.refreshToken(tokens.get(AuthConstants.REFRESH_TOKEN))
									.build())
							.build()
					);
		} catch (Exception e) {
			ErrorCode errorCode = new ErrorCode();
			errorCode.addErrorField("XXXX", "XXX", "Invalid username or password");
			String errorCodeInString = JsonConverter.convertToJsonString(errorCode);
			throw new RegisterException(errorCodeInString);
		}
	}

	@Override
	public ResponseEntity<ResponseModel> logout(LogoutRequestDTO request) {
		return null;
	}

	@Override
	public ResponseEntity<ResponseModel> autoGeneratedPassword() {
		return null;
	}
}
