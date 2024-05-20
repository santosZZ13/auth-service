package org.authen.oauth2.user;

import java.util.Map;

public class FacebookOAuth2UserInfo extends OAuth2UserInfo {

	public FacebookOAuth2UserInfo(Map<String, Object> attributes) {
		super(attributes);
	}

	@Override
	public String getId() {
		return "";
	}

	@Override
	public String getName() {
		return "";
	}

	@Override
	public String getEmail() {
		return "";
	}

	@Override
	public String getImageUrl() {
		return "";
	}

	@Override
	public String getGivenName() {
		return "";
	}

	@Override
	public String getFamilyName() {
		return "";
	}

	@Override
	public String getLocale() {
		return "";
	}

	@Override
	public String getSub() {
		return "";
	}

	@Override
	public Boolean getVerifiedEmail() {
		return null;
	}
}
