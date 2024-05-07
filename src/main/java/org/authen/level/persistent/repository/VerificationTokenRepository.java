package org.authen.level.persistent.repository;

import org.authen.level.persistent.entity.UserEntity;
import org.authen.level.persistent.entity.VerificationTokenEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Date;
import java.util.Optional;
import java.util.stream.Stream;

public interface VerificationTokenRepository extends JpaRepository<VerificationTokenEntity, Long> {

	Optional<VerificationTokenEntity> findByToken(String token);

	VerificationTokenEntity findByUser(UserEntity userEntity);

	Stream<VerificationTokenEntity> findAllByExpiryDateLessThan(Date now);

	void deleteByExpiryDateLessThan(Date now);

//	@Modifying
//	@Query("delete from VerificationToken t where t.expiryDate <= ?1")
//	void deleteAllExpiredSince(Date now);
}
