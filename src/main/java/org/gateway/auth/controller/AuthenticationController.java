package org.gateway.auth.controller;


import lombok.extern.log4j.Log4j2;
import org.gateway.auth.model.dto.login.LoginRequestDTO;
import org.gateway.auth.model.dto.register.RegisterDTO;
import org.gateway.auth.model.dto.logout.LogoutRequestDTO;
import org.gateway.auth.service.AuthenticationService;
import org.gateway.common.exception.apiEx.ResponseModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static org.gateway.auth.controller.AuthenticationController.AUTHENTICATION_ENDPOINT;

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
	public AuthenticationController(AuthenticationService authenticationService) {
		this.authenticationService = authenticationService;
	}

	@PostMapping(value = REGISTER_ENDPOINT)
	public ResponseEntity<ResponseModel> registerAccount(@RequestBody RegisterDTO request) {
		return authenticationService.registerAccount(request);
	}

	@PostMapping(value = LOGIN_ENDPOINT)
	public ResponseEntity<ResponseModel> login(@RequestBody LoginRequestDTO request) {
		return authenticationService.login(request);
	}

	//TODO: Implement the logout method
	@PostMapping(value = LOGOUT_ENDPOINT)
	public ResponseEntity<ResponseModel> logout(@RequestBody LogoutRequestDTO request) {
		return authenticationService.logout(request);
	}


}
