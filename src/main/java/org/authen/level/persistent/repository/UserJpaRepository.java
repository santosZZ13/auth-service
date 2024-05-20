package org.authen.level.persistent.repository;

import org.authen.level.persistent.entity.UserEntity;
import org.springframework.stereotype.Repository;
import org.jetbrains.annotations.NotNull;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserJpaRepository extends JpaRepository<UserEntity, Long> {
	Optional<UserEntity> findByEmail(String email);
	@NotNull List<UserEntity> findAll();
	@Override
	<S extends UserEntity> @NotNull S save(@NotNull S entity);
	@Modifying
	@Query("update user u set u.enabled = ?1 where u.email = ?2")
	void updateEnabledByUsername(boolean enabled, String username);
}
