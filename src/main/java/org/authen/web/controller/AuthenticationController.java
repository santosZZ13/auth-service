package org.authen.web.controller;


import lombok.extern.log4j.Log4j2;
import org.authen.web.dto.login.LoginRequestDTO;
import org.authen.web.dto.register.RegisterDTORequest;
import org.authen.web.dto.logout.LogoutRequestDTO;
import org.authen.web.service.AuthenticationService;
import org.authen.exception.apiEx.ResponseModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

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

	@PostMapping(value = REGISTER_ENDPOINT)
	public ResponseEntity<ResponseModel> registerAccount(@RequestBody RegisterDTORequest registerDTORequest, HttpServletRequest request) {
		log.debug("Registering user account with information: {}", registerDTORequest);
		return authenticationService.registerAccount(registerDTORequest, request);
	}

	@GetMapping(value = "/registrationConfirm")
	public ResponseEntity<ResponseModel> confirmRegistration(@RequestParam("token") String token, HttpServletRequest request) {
		log.debug("Confirming registration with token: {}", token);
		return authenticationService.confirmRegistration(token, request);
	}

	@PostMapping(value = "/resetPassword")
	public ResponseEntity<ResponseModel> resetPassword(@RequestParam("email") String email, HttpServletRequest request) {
		log.debug("Resetting password for user with email: {}", email);
		return authenticationService.resetPassword(email, request);
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
