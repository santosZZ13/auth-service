package org.authen.level.service.verificationToken;

import lombok.AllArgsConstructor;
import org.authen.level.persistent.entity.UserEntity;
import org.authen.level.persistent.entity.VerificationTokenEntity;
import org.authen.level.persistent.repository.VerificationTokenRepository;
import org.authen.level.service.model.UserModel;
import org.authen.level.service.model.VerificationTokenModel;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class VerificationTokenLogicServiceImpl implements VerificationTokenLogicService {

	private final VerificationTokenRepository verificationTokenRepository;


//	@Override
//	public void createVerificationTokenForUser(UserEntity user, String token) {
//		final VerificationToken myToken = new VerificationToken(token, user);
//		tokenRepository.save(myToken);
//	}


	@Override
	public void createVerificationTokenForUser(UserModel userModel, String token) {
		final VerificationTokenEntity myToken = new VerificationTokenEntity(token, userModel.toUserEntity());
		verificationTokenRepository.save(myToken);
	}

	@Override
	public VerificationTokenModel getVerificationToken(String VerificationToken) {
		return verificationTokenRepository
				.findByToken(VerificationToken)
				.map(verificationToken -> VerificationTokenModel.builder()
						.id(verificationToken.getId())
						.token(verificationToken.getToken())
						.expiryDate(verificationToken.getExpiryDate())
						.userModel(toUserModel(verificationToken.getUser()))
						.build())
				.orElse(null);
	}


	private UserModel toUserModel(UserEntity userEntity) {
		return UserModel.builder()
				.id(userEntity.getId())
				.password(userEntity.getPassword())
				.email(userEntity.getEmail())
				.enabled(userEntity.isEnabled())
				.type(userEntity.getType())
				.firstName(userEntity.getFirstName())
				.lastName(userEntity.getLastName())
				.build();

	}

	@Override
	public VerificationTokenModel generateNewVerificationToken(String token) {
		return null;
	}
}
