package org.authen.service.user;

import persistent.entity.UserEntity;
import persistent.entity.VerificationToken;

public interface VerificationTokenService {
	void createVerificationTokenForUser(UserEntity user, String token);
	VerificationToken getVerificationToken(String VerificationToken);
	VerificationToken  generateNewVerificationToken(String token);
}
