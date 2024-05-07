package org.authen.web.service;

import org.authen.web.dto.login.LoginRequestDTO;
import org.authen.web.dto.logout.LogoutRequestDTO;
import org.authen.wapper.model.GenericResponseWrapper;

public interface AuthenticationService {

	GenericResponseWrapper login(LoginRequestDTO request);
	GenericResponseWrapper logout(LogoutRequestDTO request);
}
