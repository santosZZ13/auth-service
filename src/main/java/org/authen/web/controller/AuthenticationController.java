package org.authen.web.controller;


import lombok.extern.log4j.Log4j2;
import org.authen.web.dto.login.LoginRequestDTO;
import org.authen.web.dto.logout.LogoutRequestDTO;
import org.authen.web.service.AuthenticationService;
import org.authen.wapper.model.GenericResponseWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.web.bind.annotation.*;

import static org.authen.web.controller.AuthenticationController.AUTHENTICATION_ENDPOINT;

@RestController
@RequestMapping(value = AUTHENTICATION_ENDPOINT)
@Log4j2
public class AuthenticationController {
	private final AuthenticationService authenticationService;
	public static final String AUTHENTICATION_ENDPOINT = "/api/auth";
	public static final String LOGIN_ENDPOINT = "/login";
	public static final String REGISTER_ENDPOINT = "/registration";
	public static final String LOGOUT_ENDPOINT = "/logout";
	public static final String REFRESH_ENDPOINT = "/refresh";


	@Autowired
	private ApplicationEventPublisher eventPublisher;


	@Autowired
	public AuthenticationController(AuthenticationService authenticationService) {
		this.authenticationService = authenticationService;
	}


	@PostMapping(value = LOGIN_ENDPOINT)
	public GenericResponseWrapper login(@RequestBody LoginRequestDTO request) {
		log.info("#Login - request: {} ", request);
		return authenticationService.login(request);
	}

	//TODO: Implement the logout method
	@PostMapping(value = LOGOUT_ENDPOINT)
	public GenericResponseWrapper logout(@RequestBody LogoutRequestDTO request) {
		return authenticationService.logout(request);
	}


}
