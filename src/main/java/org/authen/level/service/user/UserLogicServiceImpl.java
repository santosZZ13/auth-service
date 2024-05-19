package org.authen.level.service.user;

import lombok.AllArgsConstructor;
import org.authen.level.persistent.entity.UserEntity;
import org.authen.level.persistent.repository.UserJpaRepository;
import org.authen.level.service.model.UserModel;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@AllArgsConstructor
public class UserLogicServiceImpl implements UserLogicService {

	private final UserJpaRepository userJpaRepository;

	@Override
	public UserModel getUserModelByUsername(String username) {
		Optional<UserEntity> userEntity = userJpaRepository.findByUsername(username);
		return userEntity
				.map(UserModel::new)
				.orElse(null);
	}

	@Override
	public void saveUser(UserModel userModel) {
		userJpaRepository.save(userModel.toUserEntity());
	}

	@Override
	public void updateEnabledByUsername(Boolean aBoolean, String username) {
		userJpaRepository.updateEnabledByUsername(aBoolean, username);
	}

	@Override
	public UserModel getUserModelByEmail(String email) {
		return userJpaRepository.findByEmail(email)
				.map(UserModel::new)
				.orElse(null);
	}

	@Override
	public UserModel updateUserModel(UserModel userModelByEmail) {
		return new UserModel(userJpaRepository.save(userModelByEmail.toUserEntity()));
	}

	@Override
	public UserModel saveUserModel(UserModel userModel) {
		return new UserModel(userJpaRepository.save(userModel.toUserEntity()));
	}
}
