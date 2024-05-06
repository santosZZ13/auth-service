package org.authen.listener;

import lombok.extern.log4j.Log4j2;
import persistent.entity.UserEntity;
import org.authen.service.user.UserService;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
@Log4j2
public class RegistrationListener implements ApplicationListener<OnRegistrationCompleteEvent> {

	@Autowired
	private MessageSource messages;

//	@Autowired
//	private JavaMailSender mailSender;

	@Autowired
	private UserService userService;

	@Override
	public void onApplicationEvent(@NotNull OnRegistrationCompleteEvent event) {
		this.confirmRegistration(event);
	}

	private void confirmRegistration(OnRegistrationCompleteEvent event) {
		UserEntity userEntity = event.getUser();
		String token = UUID.randomUUID().toString();

		userService.createVerificationTokenForUser(userEntity, token);

//		String recipientAddress = userEntity.getEmail();
		String subject = "Registration Confirmation";
		String confirmationUrl = event.getAppUrl() + "/api/auth/registrationConfirm?token=" + token;
//		String message = messages.getMessage("message.regSucc", null, event.getLocale());

//		log.info("Recipient Address: {}", recipientAddress);
		log.info("Subject: {}", subject);
		log.info("Confirmation URL: {}",  "http://localhost:8080" + confirmationUrl);
	}
}
