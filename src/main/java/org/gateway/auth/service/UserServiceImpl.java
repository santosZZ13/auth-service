package org.gateway.auth.service;

import lombok.AllArgsConstructor;
import org.gateway.auth.enums.AuthConstants;
import org.gateway.auth.model.entity.UserEntity;
import org.gateway.auth.repository.UserJpaRepository;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.*;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class UserServiceImpl implements UserDetailsService, UserService {

	private final UserJpaRepository userJpaRepository;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		final UserEntity userEntityByUserName = this.findByUsername(username);

		if (Objects.isNull(userEntityByUserName)) {
			throw new UsernameNotFoundException("User not found");
		}

		return User
				.withUsername(userEntityByUserName.getUsername())
				.password(userEntityByUserName.getPassword())
				.authorities(this.getAuthorities(List.of(userEntityByUserName.getRole())))
				.accountExpired(false)
				.credentialsExpired(false)
				.disabled(false)
				.accountLocked(false)
				.build();
	}


	@Override
	public UserDetails toUserDetails(UserEntity userEntity) {
		return User
				.withUsername(userEntity.getUsername())
				.password(userEntity.getPassword())
				.authorities(this.getAuthorities(List.of(userEntity.getRole())))
				.accountExpired(false)
				.credentialsExpired(false)
				.disabled(false)
				.accountLocked(false)
				.build();
	}

	@Override
	public UserEntity findByUsername(String username) {
		return userJpaRepository.findByUsername(username).orElse(null);
	}


	@Override
	public boolean isUsernameExist(String username) {
		return Objects.nonNull(this.findByUsername(username));
	}

	@Override
	public void saveUser(UserEntity userEntity) {
		userJpaRepository.save(userEntity);
	}

	@Override
	public List<GrantedAuthority> getAuthorities(List<String> roles) {
		return roles.stream()
				.map(role -> new SimpleGrantedAuthority(AuthConstants.AUTHORITY_PREFIX + role))
				.collect(Collectors.toList());
	}
}
