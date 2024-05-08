package org.authen.web.controller;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.extern.log4j.Log4j2;
import org.authen.wapper.model.GenericResponseWrapper;
import org.authen.web.dto.register.RegisterDTORequest;
import org.authen.web.service.RegisterService;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Errors;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.annotation.Nonnull;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import java.util.Locale;

import static org.authen.web.controller.AuthenticationController.AUTHENTICATION_ENDPOINT;


@RestController
@RequestMapping(value = AUTHENTICATION_ENDPOINT)
@Log4j2
@AllArgsConstructor
public class RegisterController {

	private final RegisterService registerService;

	@PostMapping(value = "/registration")
	public ResponseEntity<GenericResponseWrapper> registerAccount(@RequestBody @Valid RegisterDTORequest registerDTORequest) {
		log.info("#Register - request: {} ", registerDTORequest);
		return registerService.registerAccount(registerDTORequest);
	}


	@GetMapping(value = "/registrationConfirm")
	public ResponseEntity<GenericResponseWrapper> confirmRegistration(@RequestParam("token") String token, HttpServletRequest request) {
		log.debug("Confirming registration with token: {}", token);
		return registerService.confirmRegistration(token, request);
	}

}
