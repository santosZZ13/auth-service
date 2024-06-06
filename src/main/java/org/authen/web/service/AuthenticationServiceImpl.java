package org.authen.web.service;

import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.authen.enums.AuthConstants;
import org.authen.configuration.security.jwt.JwtUtils;
import org.authen.web.exception.LoginException;
import org.authen.web.dto.login.LoginRequestDTO;
import org.authen.web.dto.login.LoginResponseDTO;
import org.authen.web.dto.logout.LogoutRequestDTO;
import org.authen.configuration.security.handler.AfterAuthenticationSuccessHandler;
import org.authen.wapper.model.GenericResponseSuccessWrapper;
import org.authen.service.user.UserService;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.*;

@Service
@AllArgsConstructor
@Log4j2
//@Transactional(readOnly = false)
public class AuthenticationServiceImpl implements AuthenticationService {

	private final UserService userService;
	private final AuthenticationManager authenticationManager;
	private final AfterAuthenticationSuccessHandler afterAuthenticationSuccessHandler;
	private final JwtUtils jwtUtils;


	@Override
	public GenericResponseSuccessWrapper login(LoginRequestDTO loginRequest) {

		final String username = loginRequest.getLoginForm().getUsername();
		final String password = loginRequest.getLoginForm().getPassword();


		Authentication authenticate;

		try {
			Authentication authentication = new UsernamePasswordAuthenticationToken(username, password);
			authenticate = authenticationManager.authenticate(authentication);

			if (authenticate.isAuthenticated()) {
				HttpServletRequest servletRequest = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest();
				HttpServletResponse servletResponse = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getResponse();
				SecurityContextHolder.getContext().setAuthentication(authenticate);
				// TODO: Implement this method in other thread to avoid blocking the main thread.
				afterAuthenticationSuccessHandler.onAuthenticationSuccess(servletRequest, servletResponse, authenticate);
			}
			Map<String, String> tokens = jwtUtils.generateTokens(authenticate);

//			Map<String, String> tokens = jwtTokenService.generateTokens(authenticate);

			return GenericResponseSuccessWrapper
					.builder()
					.data(LoginResponseDTO
							.builder()
							.accessToken(tokens.get(AuthConstants.ACCESS_TOKEN))
							.refreshToken(tokens.get(AuthConstants.REFRESH_TOKEN))
							.build())
					.build();

		} catch (Exception e) {
//			ErrorCode errorCodeEx = new ErrorCode();
//			errorCodeEx.addErrorField("XXXX", "XXX", "Invalid username or password");
//			String errorCodeInString = JsonConverter.convertToJsonString(errorCodeEx);
			log.error("#Authen login :: {} ::  Error Occurred ::  {} ", loginRequest, e.getMessage());
			throw new LoginException("Invalid user or password", "XXX", String.format("User: %s", username));
		}
	}


	@Override
	public GenericResponseSuccessWrapper logout(LogoutRequestDTO request) {
		return null;
	}

}
