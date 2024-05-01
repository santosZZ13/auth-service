package org.authen.persistence.dao;

import org.authen.persistence.model.UserEntity;
import org.jetbrains.annotations.NotNull;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

@EnableCaching
public interface UserJpaRepository extends JpaRepository<UserEntity, Long> {
	Optional<UserEntity> findByUsername(String username);
	@NotNull List<UserEntity> findAll();
	@Override
	<S extends UserEntity> @NotNull S save(@NotNull S entity);
	@Modifying
	@Query("update user u set u.enabled = ?1 where u.username = ?2")
	void updateEnabledByUsername(boolean enabled, String username);
}
