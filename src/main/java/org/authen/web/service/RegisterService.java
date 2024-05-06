package org.authen.web.service;

import org.authen.exception.apiEx.GenericResponse;
import org.authen.web.dto.register.RegisterDTORequest;
import org.springframework.http.ResponseEntity;

import javax.servlet.http.HttpServletRequest;

public interface RegisterService {
	ResponseEntity<GenericResponse> registerAccount(RegisterDTORequest registerDTORequest, HttpServletRequest request);
	ResponseEntity<GenericResponse> confirmRegistration(String token, HttpServletRequest request);
}
