package org.gateway.testapi;

import lombok.AllArgsConstructor;
import org.gateway.auth.model.entity.UserEntity;
import org.gateway.auth.repository.UserJpaRepository;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@AllArgsConstructor
public class UserController {

	private final UserJpaRepository userTestRepository;

	@GetMapping("/api/test/users")
	public List<UserEntity> getAllUsers() {
		return userTestRepository.findAll();
	}

	@GetMapping("/api/public/users")
	public String publicApis() {
		return "public";
	}
}
