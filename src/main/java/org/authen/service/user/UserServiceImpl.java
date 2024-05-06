package org.authen.service.user;

import lombok.AllArgsConstructor;
import lombok.NonNull;
import org.authen.enums.AuthConstants;
import service.model.UserModel;
import persistent.repository.VerificationTokenRepository;
import persistent.entity.UserEntity;
import persistent.repository.UserJpaRepository;
import persistent.entity.VerificationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.*;
import org.springframework.security.crypto.password.PasswordEncoder;
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
	private final PasswordEncoder passwordEncoder;


	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		final UserModel userModelByUsername = this.getUserModelByUsername(username);

		if (Objects.isNull(userModelByUsername)) {
			throw new UsernameNotFoundException("User not found");
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


//	@Override
//	public UserEntity findByEmail(String email) {
//		return userJpaRepository.findByEmail(email).orElse(null);
//	}
//
//	@Override
//	public UserEntity findByUsername(String username) {
//		return userJpaRepository.findByUsername(username).orElse(null);
//	}
//
//
//	@Override
//	public Boolean isUsernameExist(String username) {
//		return Objects.nonNull(this.findByUsername(username));
//	}



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

	@Override
	public Boolean checkIfValidOldPassword(@NonNull UserModel userModel, @NonNull String oldPassword) {
		return passwordEncoder.matches(oldPassword, userModel.getPassword());
	}

	@Override
	public void changeUserPassword(UserModel userModelByUsername, String newPassword) {
		UserEntity userEntity = userModelByUsername.toUserEntity();
		userEntity.setPassword(passwordEncoder.encode(newPassword));
		userJpaRepository.save(userEntity);
	}
}
