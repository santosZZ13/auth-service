package org.authen.web.service;

import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.authen.wapper.model.GenericResponseSuccessWrapper;
import org.authen.listener.OnRegistrationCompleteEvent;
import org.authen.service.user.UserService;
import org.authen.web.dto.register.ConfirmRegistrationResponse;
import org.authen.web.dto.register.RegisterConfig;
import org.authen.web.dto.register.RegisterDTORequest;
import org.authen.web.dto.register.RegisterDTOResponse;
import org.authen.web.exception.RegisterEventException;
import org.authen.web.exception.RegisterException;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.authen.level.service.model.UserModel;
import org.authen.level.service.model.VerificationTokenModel;
import org.authen.level.service.verificationToken.VerificationTokenLogicServiceImpl;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.servlet.http.HttpServletRequest;
import java.util.Calendar;
import java.util.Locale;
import java.util.Objects;

@Service
@Log4j2
@AllArgsConstructor
public class RegisterServiceImpl implements RegisterService {

	private final ApplicationEventPublisher eventPublisher;
	private final PasswordEncoder passwordEncoder;
	private final UserService userService;
	private final VerificationTokenLogicServiceImpl verificationTokenLogicService;

	@Override
	public GenericResponseSuccessWrapper registerAccount(RegisterDTORequest registerDTORequest) {

		log.info("#registerAccount - registerDTORequest: {} ", registerDTORequest);
		String requestUserName = registerDTORequest.getRegisterForm().getUsername();

		if (userService.isUsernameExist(requestUserName)) {
			throw new RegisterException("Username is already Exist", "XXX", String.format("User: {%s}", requestUserName));
		}

		try {
			final String hashedPassword = passwordEncoder.encode(registerDTORequest.getRegisterForm().getPassword());
			final UserModel userModelWithHashPasswordToSaveInDB = registerDTORequest.toUserModelWithHashPasswordToSaveInDB(hashedPassword);
			final RegisterConfig registerConfig = new RegisterConfig(userModelWithHashPasswordToSaveInDB);
			RegisterDTOResponse registerResponse = RegisterDTOResponse.builder()
					.registerConfig(registerConfig)
					.build();

			// Publish event
			try {
				final Locale locale = LocaleContextHolder.getLocale();
				final String contextPath = ServletUriComponentsBuilder.fromCurrentContextPath().build().toUriString();
				eventPublisher.publishEvent(new OnRegistrationCompleteEvent(
						userModelWithHashPasswordToSaveInDB,
						locale,
						contextPath));

			} catch (Exception e) {
				log.info("#registerAccount - Error: {}", e.getMessage());
				throw new RegisterEventException("Error occurred while sending the token to email", "XXX", String.format("User: {%s}", requestUserName));
			}

			return GenericResponseSuccessWrapper
					.builder()
					.data(registerResponse)
					.build();
		} catch (Exception e) {
			log.info("#registerAccount - Error: {}", e.getMessage());
			throw e;
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
