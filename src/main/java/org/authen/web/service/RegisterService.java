package org.authen.web.service;

import org.authen.wapper.model.GenericResponseWrapper;
import org.authen.web.dto.register.RegisterDTORequest;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;

import javax.servlet.http.HttpServletRequest;

public interface RegisterService {
	ResponseEntity<GenericResponseWrapper> registerAccount(RegisterDTORequest registerDTORequest, HttpServletRequest request, Errors errors);
	ResponseEntity<GenericResponseWrapper> confirmRegistration(String token, HttpServletRequest request);
}
