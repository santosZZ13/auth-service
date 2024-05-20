package org.authen.config.security;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@ConfigurationProperties(prefix = "app")
@EnableConfigurationProperties(AppProperties.class)
@Getter
@Component
public class AppProperties {
	private final Auth auth = new Auth();
	private final OAuth2 oauth2 = new OAuth2();


	@Getter
	@Setter
	public static final class Auth {
		private String tokenSecret;
		private long tokenExpirationMsec;
	}

	@Getter
	@Setter
	public static final class OAuth2 {
		private List<String> authorizedRedirectUris = new ArrayList<>();
		private String redirectUri;

		public OAuth2 authorizedRedirectUris(List<String> authorizedRedirectUris) {
			this.authorizedRedirectUris = authorizedRedirectUris;
			return this;
		}
	}
}

