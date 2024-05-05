package org.authen.web.service;

import org.authen.web.dto.login.LoginRequestDTO;
import org.authen.web.dto.register.RegisterDTORequest;
import org.authen.web.dto.logout.LogoutRequestDTO;
import org.authen.exception.apiEx.ResponseModel;
import org.springframework.http.ResponseEntity;

import javax.servlet.http.HttpServletRequest;

public interface AuthenticationService {
	ResponseEntity<ResponseModel> registerAccount(RegisterDTORequest registerDTORequest, HttpServletRequest request);
	ResponseEntity<ResponseModel> autoGeneratedPassword();
	ResponseEntity<ResponseModel> login(LoginRequestDTO request);
	ResponseEntity<ResponseModel> logout(LogoutRequestDTO request);
	ResponseEntity<ResponseModel> confirmRegistration(String token, HttpServletRequest request);
	ResponseEntity<ResponseModel> resetPassword(String email, HttpServletRequest request);
}