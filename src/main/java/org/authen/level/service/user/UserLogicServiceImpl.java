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
}
