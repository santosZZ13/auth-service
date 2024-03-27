package org.gateway.auth.service;

import org.springframework.stereotype.Service;

@Service
public class ValidateRegisterImpl implements ValidateRegister {

	//TODO: Implement this method to validate the username
	@Override
	public boolean validateUsername(String username) {
		return true;
	}

	//TODO: Implement this method to validate the password
	@Override
	public boolean validatePassword(String password) {
		return true;
	}

	//TODO: Implement this method to validate the role name
	@Override
	public boolean validateRoleName(String roleName) {
		return true;
	}
}
