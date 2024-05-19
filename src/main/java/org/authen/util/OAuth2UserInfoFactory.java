package org.authen.util;

import org.authen.exception.OAuth2AuthenticationProcessingException;
import org.authen.oauth2.user.FacebookOAuth2UserInfo;
import org.authen.oauth2.user.GithubOAuth2UserInfo;
import org.authen.oauth2.user.GoogleOAuth2UserInfo;
import org.authen.oauth2.user.OAuth2UserInfo;

import java.util.Map;

public class OAuth2UserInfoFactory {
	public static OAuth2UserInfo getOAuth2UserInfo(String registrationId, Map<String, Object> attributes) {
		if(registrationId.equalsIgnoreCase("google")) {
			return new GoogleOAuth2UserInfo(attributes);
		} else if (registrationId.equalsIgnoreCase("facebook")) {
			return new FacebookOAuth2UserInfo(attributes);
		} else if (registrationId.equalsIgnoreCase("github")) {
			return new GithubOAuth2UserInfo(attributes);
		} else {
			throw new OAuth2AuthenticationProcessingException("Sorry! Login with " + registrationId + " is not supported yet.");
		}
	}
}
