package org.authen.repo;

import org.authen.model.entity.UserEntity;
import org.jetbrains.annotations.NotNull;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface UserJpaRepository extends JpaRepository<UserEntity, String> {
	Optional<UserEntity> findByUsername(String username);
	@NotNull List<UserEntity> findAll();
}
