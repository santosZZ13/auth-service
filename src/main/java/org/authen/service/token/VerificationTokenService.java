package org.authen.service.token;

import org.authen.level.service.model.UserModel;
import org.authen.level.service.model.VerificationTokenModel;

public interface VerificationTokenService {
	void createVerificationTokenForUser(UserModel user, String token);
	VerificationTokenModel getVerificationToken(String VerificationToken);
	VerificationTokenModel  generateNewVerificationToken(String token);
}
