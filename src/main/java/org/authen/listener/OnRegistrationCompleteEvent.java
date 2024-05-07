package org.authen.listener;

import lombok.Getter;
import lombok.Setter;
import org.springframework.context.ApplicationEvent;
import org.authen.level.service.model.UserModel;

import java.util.Locale;

@Getter
@Setter
public class OnRegistrationCompleteEvent extends ApplicationEvent {
	private String appUrl;
	private Locale locale;
	private UserModel user;

	public OnRegistrationCompleteEvent(UserModel user, Locale locale, String appUrl) {
		super(user);
		this.user = user;
		this.locale = locale;
		this.appUrl = appUrl;
	}
}
