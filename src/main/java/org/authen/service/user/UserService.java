package org.authen.service.user;

import service.model.UserModel;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.List;

public interface UserService extends VerificationTokenService {
	UserDetails toUserDetails(UserModel userModel);
//	Boolean isUsernameExist(String username);

	List<GrantedAuthority> getAuthorities(List<String> roles);
	void updateEnabledByUsername(Boolean aTrue, String username);


	UserModel getUserModelByUsername(String username);
	Boolean checkIfValidOldPassword(UserModel userModel, String oldPassword);
	void changeUserPassword(UserModel userModelByUsername, String newPassword);
}
