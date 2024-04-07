package org.authen.service.validation;

public interface ValidateRegister {
	boolean validateUsername(String username);

	boolean validatePassword(String password);

	boolean validateRoleName(String roleName);
}
