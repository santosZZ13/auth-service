package org.authen.web.controller;

import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.authen.wapper.model.GenericResponseWrapper;
import org.authen.web.dto.register.RegisterDTORequest;
import org.authen.web.service.RegisterService;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Errors;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import static org.authen.web.controller.AuthenticationController.AUTHENTICATION_ENDPOINT;


@RestController
@RequestMapping(value = AUTHENTICATION_ENDPOINT)
@Log4j2
@AllArgsConstructor
@Validated
public class RegisterController {

	private final RegisterService registerService;

	@PostMapping(value = "/registration")
	@Validated
	public ResponseEntity<GenericResponseWrapper> registerAccount(@Valid @RequestBody RegisterDTORequest registerDTORequest,
																  HttpServletRequest request,
																  Errors errors,
																  BindingResult bindingResult) {
		log.info("#Register - request: {} ", registerDTORequest);
		return registerService.registerAccount(registerDTORequest, request, errors);
	}

	@GetMapping(value = "/registrationConfirm")
	public ResponseEntity<GenericResponseWrapper> confirmRegistration(@RequestParam("token") String token, HttpServletRequest request) {
		log.debug("Confirming registration with token: {}", token);
		return registerService.confirmRegistration(token, request);
	}
}
