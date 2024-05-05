package org.authen.service.user;

import lombok.AllArgsConstructor;
import org.authen.enums.AuthConstants;
import org.authen.model.UserModel;
import org.authen.persistence.dao.VerificationTokenRepository;
import org.authen.persistence.model.UserEntity;
import org.authen.persistence.dao.UserJpaRepository;
import org.authen.persistence.model.VerificationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.*;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class UserServiceImpl implements UserDetailsService, UserService {

	private final UserJpaRepository userJpaRepository;
	private final VerificationTokenRepository tokenRepository;

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
	public UserEntity findByEmail(String email) {
		return userJpaRepository.findByEmail(email).orElse(null);
	}

	@Override
	public UserEntity findByUsername(String username) {
		return userJpaRepository.findByUsername(username).orElse(null);
	}


	@Override
	public Boolean isUsernameExist(String username) {
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

	@Override
	public void updateEnabledByUsername(Boolean aTrue, String username) {
		userJpaRepository.updateEnabledByUsername(aTrue, username);
	}

	@Override
	public void createVerificationTokenForUser(UserEntity user, String token) {
		final VerificationToken myToken = new VerificationToken(token, user);
		tokenRepository.save(myToken);
	}

	@Override
	public VerificationToken getVerificationToken(String VerificationToken) {
		return tokenRepository.findByToken(VerificationToken);
	}

	@Override
	public VerificationToken  generateNewVerificationToken(String token) {
		return null;
	}


	@Override
	public UserModel getUserModelByUsername(String username) {
		Optional<UserEntity> userEntity = userJpaRepository.findByUsername(username);
		return userEntity.map(UserModel::new).orElse(null);
	}
}
