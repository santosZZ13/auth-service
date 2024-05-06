package persistent.repository;

import persistent.entity.UserEntity;
import persistent.entity.VerificationToken;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Date;
import java.util.stream.Stream;

public interface VerificationTokenRepository extends JpaRepository<VerificationToken, Long> {

	VerificationToken findByToken(String token);

	VerificationToken findByUser(UserEntity userEntity);

	Stream<VerificationToken> findAllByExpiryDateLessThan(Date now);

	void deleteByExpiryDateLessThan(Date now);

//	@Modifying
//	@Query("delete from VerificationToken t where t.expiryDate <= ?1")
//	void deleteAllExpiredSince(Date now);
}
