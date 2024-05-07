package org.authen.web.service;

import org.authen.wapper.model.GenericResponseWrapper;
import org.authen.web.dto.pw.UpdatePasswordDTO;
import org.springframework.http.ResponseEntity;

import javax.servlet.http.HttpServletRequest;

public interface PasswordService {
	ResponseEntity<GenericResponseWrapper> resetPassword(String email, HttpServletRequest request);
	ResponseEntity<GenericResponseWrapper> changeUserPassword(UpdatePasswordDTO updatePasswordDTO);

}
