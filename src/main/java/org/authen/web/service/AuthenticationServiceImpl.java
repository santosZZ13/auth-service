package org.authen.web.service;

import lombok.AllArgsConstructor;
import service.model.UserModel;
import org.authen.enums.AuthConstants;
import org.authen.exception.registerEx.RegisterException;
import org.authen.web.dto.login.LoginRequestDTO;
import org.authen.web.dto.login.LoginResponseDTO;
import org.authen.web.dto.logout.LogoutRequestDTO;
import org.authen.filter.AfterAuthenticationSuccessHandler;
import org.authen.jwt.JwtTokenService;
import org.authen.exception.apiEx.GenericResponse;
import org.authen.service.validation.ValidateRegister;
import org.authen.service.user.UserService;
import org.authen.util.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.*;

@Service
@AllArgsConstructor
//@Transactional(readOnly = false)
public class AuthenticationServiceImpl implements AuthenticationService {

	private static final Logger log = LoggerFactory.getLogger(AuthenticationServiceImpl.class);
	private final UserService userService;
	private final ValidateRegister validateRegister;
	private final PasswordEncoder passwordEncoder;
	private final AuthenticationManager authenticationManager;
	private final JwtTokenService jwtTokenService;
	private final ApplicationEventPublisher eventPublisher;
	private final AfterAuthenticationSuccessHandler afterAuthenticationSuccessHandler;



	@Override
	public ResponseEntity<GenericResponse> login(LoginRequestDTO loginRequest) {
		final String username = loginRequest.getUsername();
		final String password = loginRequest.getPassword();
		UserModel userModel = userService.getUserModelByUsername(username);
		ErrorCode errorCode = new ErrorCode();

		if (Objects.isNull(userModel)) {
			errorCode.addErrorField("XXXX", "XXX", String.format("User with username %s not found", username));
			String errorCodeInString = JsonConverter.convertToJsonString(errorCode);
			throw new RegisterException(errorCodeInString);
		}


		Authentication authenticate;

		try {
			Authentication authentication = new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword(), userService.getAuthorities(List.of(userModel.getRole())));
			authenticate = authenticationManager.authenticate(authentication);

			if (authenticate.isAuthenticated()) {
				HttpServletRequest servletRequest = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest();
				HttpServletResponse servletResponse = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getResponse();
				SecurityContextHolder.getContext().setAuthentication(authenticate);
				// TODO: Implement this method in other thread to avoid blocking the main thread.
				afterAuthenticationSuccessHandler.onAuthenticationSuccess(servletRequest, servletResponse, authenticate);
			}

			Map<String, String> tokens = jwtTokenService.generateTokens(authenticate, userModel);
			return ResponseEntity
					.ok()
					.body(GenericResponse
							.builder()
							.success(Boolean.TRUE)
							.data(LoginResponseDTO
									.builder()
									.accessToken(tokens.get(AuthConstants.ACCESS_TOKEN))
									.refreshToken(tokens.get(AuthConstants.REFRESH_TOKEN))
									.build())
							.build()
					);
		} catch (Exception e) {
			ErrorCode errorCodeEx = new ErrorCode();
			errorCodeEx.addErrorField("XXXX", "XXX", "Invalid username or password");
			String errorCodeInString = JsonConverter.convertToJsonString(errorCodeEx);
			throw new RegisterException(errorCodeInString);
		}
	}

	@Override
	public ResponseEntity<GenericResponse> logout(LogoutRequestDTO request) {
		return null;
	}

}
