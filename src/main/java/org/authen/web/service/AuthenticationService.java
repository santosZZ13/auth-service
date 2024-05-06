package org.authen.web.service;

import org.authen.web.dto.login.LoginRequestDTO;
import org.authen.web.dto.register.RegisterDTORequest;
import org.authen.web.dto.logout.LogoutRequestDTO;
import org.authen.exception.apiEx.GenericResponse;
import org.springframework.http.ResponseEntity;

import javax.servlet.http.HttpServletRequest;

public interface AuthenticationService {
	ResponseEntity<GenericResponse> login(LoginRequestDTO request);
	ResponseEntity<GenericResponse> logout(LogoutRequestDTO request);
}
