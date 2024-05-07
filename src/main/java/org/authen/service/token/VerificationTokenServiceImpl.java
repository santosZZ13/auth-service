package org.authen.service.token;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.authen.level.service.model.UserModel;
import org.authen.level.service.model.VerificationTokenModel;
import org.authen.level.service.verificationToken.VerificationTokenLogicService;

@Service
@AllArgsConstructor
public class VerificationTokenServiceImpl implements VerificationTokenService {


	private final VerificationTokenLogicService verificationTokenLogicService;


	@Override
	public void createVerificationTokenForUser(UserModel user, String token) {
		verificationTokenLogicService.createVerificationTokenForUser(user, token);
	}

	@Override
	public VerificationTokenModel getVerificationToken(String VerificationToken) {
		return verificationTokenLogicService.getVerificationToken(VerificationToken);
	}

	@Override
	public VerificationTokenModel generateNewVerificationToken(String token) {
		return verificationTokenLogicService.generateNewVerificationToken(token);
	}
}
