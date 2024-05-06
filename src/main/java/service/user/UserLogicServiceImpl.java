package service.user;

import lombok.AllArgsConstructor;
import service.model.UserModel;
import org.springframework.stereotype.Service;
import persistent.entity.UserEntity;
import persistent.repository.UserJpaRepository;

import java.util.Optional;

@Service
@AllArgsConstructor
public class UserLogicServiceImpl implements UserLogicService {

	private final UserJpaRepository userJpaRepository;

	@Override
	public UserModel getUserModelByUsername(String username) {
		Optional<UserEntity> userEntity = userJpaRepository.findByUsername(username);
		return userEntity.map(UserModel::new).orElse(null);
	}

	@Override
	public void saveUser(UserModel userModel) {
		userJpaRepository.save(userModel.toUserEntity());
	}
}
