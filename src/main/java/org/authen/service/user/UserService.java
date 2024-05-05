package org.authen.service.user;

import org.authen.model.UserModel;
import org.authen.persistence.model.UserEntity;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.List;

public interface UserService extends VerificationTokenService {
	UserEntity findByUsername(String username);
	UserEntity findByEmail(String email);
	UserDetails toUserDetails(UserEntity userEntity);
	Boolean isUsernameExist(String username);
	void saveUser(UserEntity userEntity);
	List<GrantedAuthority> getAuthorities(List<String> roles);
	void updateEnabledByUsername(Boolean aTrue, String username);


	UserModel getUserModelByUsername(String username);
}
