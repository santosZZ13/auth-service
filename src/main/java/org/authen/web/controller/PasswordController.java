package org.authen.web.controller;

import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.authen.wapper.model.GenericResponseWrapper;
import org.authen.web.dto.pw.UpdatePasswordDTO;
import org.authen.web.service.PasswordService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

import java.util.Locale;

import static org.authen.web.controller.AuthenticationController.AUTHENTICATION_ENDPOINT;

@RestController
@RequestMapping(value = AUTHENTICATION_ENDPOINT)
@Log4j2
@AllArgsConstructor
public class PasswordController {

	private PasswordService passwordService;

	@PostMapping(value = "/password/reset")
	public ResponseEntity<GenericResponseWrapper> resetPassword(@RequestParam("email") String email, HttpServletRequest request) {
		log.info("#Resetting password for user with email: {}", email);
		return passwordService.resetPassword(email, request);
	}

	@PostMapping(value = "/password/update")
	public ResponseEntity<GenericResponseWrapper> changeUserPassword(Locale locale, @RequestBody UpdatePasswordDTO updatePasswordDTO) {
		log.info("Updating password for user with email: {}", locale);
		return passwordService.changeUserPassword(updatePasswordDTO);
	}
}
