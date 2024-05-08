package org.authen.web.service;

import org.authen.web.dto.login.LoginRequestDTO;
import org.authen.web.dto.logout.LogoutRequestDTO;
import org.authen.wapper.model.GenericResponseSuccessWrapper;

public interface AuthenticationService {

	GenericResponseSuccessWrapper login(LoginRequestDTO request);
	GenericResponseSuccessWrapper logout(LogoutRequestDTO request);
}
