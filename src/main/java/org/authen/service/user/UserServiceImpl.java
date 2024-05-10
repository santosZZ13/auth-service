package org.authen.service.user;

import lombok.AllArgsConstructor;
import org.authen.enums.AuthConstants;
import org.authen.level.service.model.UserModel;
import org.authen.web.dto.login.LoginRequestDTO;
import org.authen.web.exception.LoginException;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.*;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.authen.level.service.user.UserLogicService;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class UserServiceImpl implements UserDetailsService, UserService {

	private final UserLogicService userLogicService;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

		final UserModel userModelByUsername = this.getUserModelByUsername(username);

		if (Objects.isNull(userModelByUsername)) {
			throw new LoginException("Not found the user in our system", "XXX", String.format("User: %s", username));
		}

		return User
				.withUsername(userModelByUsername.getUsername())
				.password(userModelByUsername.getPassword())
				.authorities(this.getAuthorities(List.of(userModelByUsername.getRole())))
				.accountExpired(false)
				.credentialsExpired(false)
				.disabled(false)
				.accountLocked(false)
				.build();
	}



	@Override
	public UserDetails toUserDetails(UserModel userModel) {
		return User
				.withUsername(userModel.getUsername())
				.password(userModel.getPassword())
				.authorities(this.getAuthorities(List.of(userModel.getRole())))
				.accountExpired(false)
				.credentialsExpired(false)
				.disabled(false)
				.accountLocked(false)
				.build();
	}


	@Override
	public List<GrantedAuthority> getAuthorities(List<String> roles) {
		return roles.stream()
				.map(role -> new SimpleGrantedAuthority(AuthConstants.AUTHORITY_PREFIX + role))
				.collect(Collectors.toList());
	}

	@Override
	public void updateEnabledByUsername(Boolean aTrue, String username) {
		userLogicService.updateEnabledByUsername(aTrue, username);
	}


	@Override
	public UserModel getUserModelByUsername(String username) {
		return userLogicService.getUserModelByUsername(username);
	}

	@Override
	public Boolean isUsernameExist(String username) {
		return userLogicService.getUserModelByUsername(username) != null;
	}
}
