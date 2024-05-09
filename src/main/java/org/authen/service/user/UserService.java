package org.authen.service.user;

import org.authen.level.service.model.UserModel;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.List;

public interface UserService {
	UserDetails toUserDetails(UserModel userModel);
	Boolean isUsernameExist(String username);
	List<GrantedAuthority> getAuthorities(List<String> roles);
	void updateEnabledByUsername(Boolean aTrue, String username);
	UserModel getUserModelByUsername(String username);
}
