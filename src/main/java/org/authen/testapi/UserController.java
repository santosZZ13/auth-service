package org.authen.testapi;

import lombok.AllArgsConstructor;
import persistent.entity.UserEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@AllArgsConstructor
public class UserController {

	private final UserService userService;

	@GetMapping("/api/test/users")
	public List<UserEntity> getAllUsers() {
		return userService.getAllUsers();
	}

}
