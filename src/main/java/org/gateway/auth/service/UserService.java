package org.gateway.auth.service;

import org.gateway.auth.model.entity.UserEntity;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.List;

public interface UserService {
	UserEntity findByUsername(String username);
	UserDetails toUserDetails(UserEntity userEntity);

	boolean isUsernameExist(String username);

	void saveUser(UserEntity userEntity);

	List<GrantedAuthority> getAuthorities(List<String> roles);
}
