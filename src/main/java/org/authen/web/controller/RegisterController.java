package org.authen.web.controller;

import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.authen.wapper.model.GenericResponseSuccessWrapper;
import org.authen.web.dto.register.RegisterDTORequest;
import org.authen.web.service.RegisterService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import static org.authen.web.controller.AuthenticationController.AUTHENTICATION_ENDPOINT;


@RestController
@RequestMapping(value = AUTHENTICATION_ENDPOINT)
@Log4j2
@AllArgsConstructor
public class RegisterController {

	private final RegisterService registerService;

	@PostMapping(value = "/registration")
	public GenericResponseSuccessWrapper registerAccount(@RequestBody @Valid RegisterDTORequest registerDTORequest) {
		log.info("#Register - request: {} ", registerDTORequest);
		return registerService.registerAccount(registerDTORequest);
	}


	@GetMapping(value = "/registrationConfirm")
	public ResponseEntity<GenericResponseSuccessWrapper> confirmRegistration(@RequestParam("token") String token, HttpServletRequest request) {
		log.debug("Confirming registration with token: {}", token);
		return registerService.confirmRegistration(token, request);
	}

}
