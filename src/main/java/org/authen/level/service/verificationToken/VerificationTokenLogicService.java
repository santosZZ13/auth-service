package org.authen.level.service.verificationToken;

import org.authen.level.service.model.UserModel;
import org.authen.level.service.model.VerificationTokenModel;

public interface VerificationTokenLogicService {
	void createVerificationTokenForUser(UserModel userModel, String token);
	VerificationTokenModel getVerificationToken(String VerificationToken);
	VerificationTokenModel  generateNewVerificationToken(String token);
}
