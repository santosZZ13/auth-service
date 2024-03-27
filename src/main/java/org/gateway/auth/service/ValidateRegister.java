package org.gateway.auth.service;

public interface ValidateRegister {
	boolean validateUsername(String username);

	boolean validatePassword(String password);

	boolean validateRoleName(String roleName);
}
