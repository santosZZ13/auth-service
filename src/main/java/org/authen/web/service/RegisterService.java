package org.authen.web.service;

import org.authen.wapper.model.GenericResponseSuccessWrapper;
import org.authen.web.dto.register.RegisterDTORequest;
import org.springframework.http.ResponseEntity;

import javax.servlet.http.HttpServletRequest;

public interface RegisterService {
	ResponseEntity<GenericResponseSuccessWrapper> registerAccount(RegisterDTORequest registerDTORequest);
	ResponseEntity<GenericResponseSuccessWrapper> confirmRegistration(String token, HttpServletRequest request);
}
