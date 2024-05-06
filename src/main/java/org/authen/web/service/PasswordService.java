package org.authen.web.service;

import org.authen.exception.apiEx.GenericResponse;
import org.authen.web.dto.pw.UpdatePasswordDTO;
import org.springframework.http.ResponseEntity;

import javax.servlet.http.HttpServletRequest;
import java.util.Locale;

public interface PasswordService {
	ResponseEntity<GenericResponse> resetPassword(String email, HttpServletRequest request);
	ResponseEntity<GenericResponse> changeUserPassword(UpdatePasswordDTO updatePasswordDTO);

}
