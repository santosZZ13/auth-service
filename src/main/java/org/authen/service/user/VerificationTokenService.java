package org.authen.service.user;

import org.authen.persistence.model.UserEntity;
import org.authen.persistence.model.VerificationToken;

public interface VerificationTokenService {
	void createVerificationTokenForUser(UserEntity user, String token);
	VerificationToken getVerificationToken(String VerificationToken);
	VerificationToken  generateNewVerificationToken(String token);
}
