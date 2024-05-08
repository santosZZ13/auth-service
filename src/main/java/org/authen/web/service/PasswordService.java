package org.authen.web.service;

import org.authen.wapper.model.GenericResponseSuccessWrapper;
import org.authen.web.dto.pw.UpdatePasswordDTO;
import org.springframework.http.ResponseEntity;

import javax.servlet.http.HttpServletRequest;

public interface PasswordService {
	ResponseEntity<GenericResponseSuccessWrapper> resetPassword(String email, HttpServletRequest request);
	ResponseEntity<GenericResponseSuccessWrapper> changeUserPassword(UpdatePasswordDTO updatePasswordDTO);

}
