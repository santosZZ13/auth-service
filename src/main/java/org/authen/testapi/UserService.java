package org.authen.testapi;

import lombok.AllArgsConstructor;
import org.authen.model.entity.UserEntity;
import org.authen.repo.UserJpaRepository;
import org.authen.util.HashMapping;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@EnableCaching
@AllArgsConstructor
public class UserService {
	private final UserJpaRepository userJpaRepository;
	private HashMapping<UserEntity> hashMapping;


//	@Cacheable("users")
	public List<UserEntity> getAllUsers() {
		UserEntity build = UserEntity.builder()
				.id("1")
				.username("user1")
				.password("password1")
				.role("role1")
				.build();
		hashMapping.writeHash("user1", build);
		return userJpaRepository.findAll();
	}
}
