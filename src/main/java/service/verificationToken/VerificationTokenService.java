package service.verificationToken;

public interface VerificationTokenService {
	void createVerificationTokenForUser(UserEntity user, String token);
	VerificationToken getVerificationToken(String VerificationToken);
	VerificationToken  generateNewVerificationToken(String token);
}
