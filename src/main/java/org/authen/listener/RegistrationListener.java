package org.authen.listener;

import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.authen.service.token.VerificationTokenService;
import org.authen.service.user.UserService;
import org.jetbrains.annotations.NotNull;
import org.springframework.context.ApplicationListener;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Component;
import org.authen.level.service.model.UserModel;

import java.util.UUID;

@Component
@Log4j2
@AllArgsConstructor
public class RegistrationListener implements ApplicationListener<OnRegistrationCompleteEvent> {

	private final MessageSource messages;
//	private JavaMailSender mailSender;
	private final UserService userService;
	private final VerificationTokenService verificationTokenService;


	@Override
	public void onApplicationEvent(@NotNull OnRegistrationCompleteEvent event) {
		this.confirmRegistration(event);
	}

	private void confirmRegistration(OnRegistrationCompleteEvent event) {
		UserModel userModel = event.getUser();
		String token = UUID.randomUUID().toString();

		verificationTokenService.createVerificationTokenForUser(userModel, token);

//		String recipientAddress = userEntity.getEmail();
		String subject = "Registration Confirmation";
		String confirmationUrl = event.getAppUrl() + "/api/auth/registrationConfirm?token=" + token;
//		String message = messages.getMessage("message.regSucc", null, event.getLocale());

//		log.info("Recipient Address: {}", recipientAddress);
		log.info("Subject: {}", subject);
		log.info("Confirmation URL: {}",  "http://localhost:8080" + confirmationUrl);
	}
}
