package org.authen.filter;

import lombok.AllArgsConstructor;
import org.authen.config.security.AppProperties;
import org.authen.config.security.HttpCookieOAuth2AuthorizationRequestRepository;
import org.authen.exception.BadRequestException;
import org.authen.jwt.JwtTokenService;
import org.authen.util.cookie.CookieUtils;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Service;
import org.springframework.web.util.UriComponentsBuilder;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.URI;
import java.util.Optional;

import static org.authen.config.security.HttpCookieOAuth2AuthorizationRequestRepository.REDIRECT_URI_PARAM_COOKIE_NAME;

@Service
@AllArgsConstructor
public class AfterOauth2SuccessHandler extends SimpleUrlAuthenticationSuccessHandler {

	private final JwtTokenService jwtTokenService;
	private final AppProperties appProperties;
	private HttpCookieOAuth2AuthorizationRequestRepository httpCookieOAuth2AuthorizationRequestRepository;

	@Override
	public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authentication) throws IOException, ServletException {
		super.onAuthenticationSuccess(request, response, chain, authentication);
	}

	@Override
	public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
		String targetUrl = determineTargetUrl(request, response, authentication);

		if (response.isCommitted()) {
			logger.debug("Response has already been committed. Unable to redirect to " + targetUrl);
			return;
		}

		clearAuthenticationAttributes(request, response);
		getRedirectStrategy().sendRedirect(request, response, targetUrl);
	}


	protected void clearAuthenticationAttributes(HttpServletRequest request, HttpServletResponse response) {
		super.clearAuthenticationAttributes(request);
		httpCookieOAuth2AuthorizationRequestRepository.removeAuthorizationRequestCookies(request, response);
	}


	protected String determineTargetUrl(HttpServletRequest request, HttpServletResponse response, Authentication authentication) {
		Optional<String> redirectUri = CookieUtils.getCookie(request, REDIRECT_URI_PARAM_COOKIE_NAME)
				.map(Cookie::getValue);
		// http://localhost:3000/oauth2/redirect

		if (redirectUri.isPresent() && !isAuthorizedRedirectUri(redirectUri.get())) {
			throw new BadRequestException("Sorry! We've got an Unauthorized Redirect URI and can't proceed with the authentication");
		}

		String targetUrl = redirectUri.orElse(getDefaultTargetUrl());

//		String token = tokenProvider.createToken(authentication);
		String token = "test";

		return UriComponentsBuilder.fromUriString(targetUrl)
				.queryParam("token", token)
				.build().toUriString();
	}



	private boolean isAuthorizedRedirectUri(String uri) {
		URI clientRedirectUri = URI.create(uri);

		return appProperties.getOauth2().getAuthorizedRedirectUris()
				.stream()
				.anyMatch(authorizedRedirectUri -> {
					// Only validate host and port. Let the clients use different paths if they want to
					URI authorizedURI = URI.create(authorizedRedirectUri);
					if (authorizedURI.getHost().equalsIgnoreCase(clientRedirectUri.getHost())
							&& authorizedURI.getPort() == clientRedirectUri.getPort()) {
						return true;
					}
					return false;
				});
	}
}
