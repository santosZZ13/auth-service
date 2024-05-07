package org.authen.web.controller;

import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.authen.wapper.model.GenericResponseWrapper;
import org.authen.web.dto.register.RegisterDTORequest;
import org.authen.web.service.RegisterService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

import static org.authen.web.controller.AuthenticationController.AUTHENTICATION_ENDPOINT;


@RestController
@RequestMapping(value = AUTHENTICATION_ENDPOINT)
@Log4j2
@AllArgsConstructor
public class RegisterController {

	private final RegisterService registerService;

	@PostMapping(value = "/registration")
	public ResponseEntity<GenericResponseWrapper> registerAccount(@RequestBody RegisterDTORequest registerDTORequest, HttpServletRequest request) {
		log.debug("Registering user account with information: {}", registerDTORequest);
		return registerService.registerAccount(registerDTORequest, request);
	}

	@GetMapping(value = "/registrationConfirm")
	public ResponseEntity<GenericResponseWrapper> confirmRegistration(@RequestParam("token") String token, HttpServletRequest request) {
		log.debug("Confirming registration with token: {}", token);
		return registerService.confirmRegistration(token, request);
	}
}
