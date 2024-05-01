package org.authen.service.user;

import org.authen.persistence.model.UserEntity;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.List;

public interface UserService extends VerificationTokenService {
	UserEntity findByUsername(String username);
	UserDetails toUserDetails(UserEntity userEntity);
	Boolean isUsernameExist(String username);
	void saveUser(UserEntity userEntity);
	List<GrantedAuthority> getAuthorities(List<String> roles);
	void updateEnabledByUsername(Boolean aTrue, String username);
}
