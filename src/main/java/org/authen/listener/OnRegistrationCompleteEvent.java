package org.authen.listener;

import lombok.Getter;
import lombok.Setter;
import persistent.entity.UserEntity;
import org.springframework.context.ApplicationEvent;

import java.util.Locale;

@Getter
@Setter
public class OnRegistrationCompleteEvent extends ApplicationEvent {
	private String appUrl;
	private Locale locale;
	private UserEntity user;

	public OnRegistrationCompleteEvent(UserEntity user, Locale locale, String appUrl) {
		super(user);
		this.user = user;
		this.locale = locale;
		this.appUrl = appUrl;
	}
}
