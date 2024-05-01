package org.authen;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.authen.model.entity.UserEntity;
import org.authen.repo.UserJpaRepository;
import org.authen.testapi.model_test.Role;
import org.authen.testapi.model_test.User;
import org.authen.testapi.repo_test.RoleRepository;
import org.authen.testapi.repo_test.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.FileInputStream;
import java.io.InputStream;
import java.util.List;

@SpringBootApplication
@EnableScheduling
@AllArgsConstructor
@Log4j2
@RestController
@EnableCaching
public class App implements CommandLineRunner {

	private final RoleRepository roleRepository;
	private final UserRepository userRepository;
	private final UserJpaRepository userJpaRepository;

	@GetMapping("/api/public/users")
	public Iterable<User> getAllUsers() {
		return userRepository.findAll();
	}

	@GetMapping("/api/public/users/jpa")
	@Cacheable("users")
	public List<UserEntity> getAllUsersJpa() {
		return userJpaRepository.findAll();
	}

	public static void main(String[] args) {
		SpringApplication.run(App.class, args);
	}

	@Override
	public void run(String... args) throws Exception {

//		Role roleUser = Role.builder().name("ROLE_USER").build();
//		Role roleAdmin = Role.builder().name("ROLE_ADMIN").build();
//
//		roleRepository.save(roleUser);
//		roleRepository.save(roleAdmin);

//		roleRepository.findAll().forEach(role -
//
//		if (userRepository.count() == 0) {
//			Role roleUser = roleRepository.findFirstByName("ROLE_USER");
//			Role roleAdmin = roleRepository.findFirstByName("ROLE_ADMIN");
//
//			// create a Jackson object mapper
//			ObjectMapper mapper = new ObjectMapper();
//			// create a type definition to convert the array of JSON into a List of Users
//			TypeReference<List<User>> typeReference = new TypeReference<List<User>>() {};
//			InputStream resourceAsStream = new FileInputStream("src/main/resources/db/user.json");
//			List<User> users = mapper.readValue(resourceAsStream, typeReference);
//
//			users.forEach(user -> {
//				user.addRole(roleUser);
//				userRepository.save(user);
//			});
//
//			User adminUser = new User();
//			adminUser.setName("Adminus Admistradore");
//			adminUser.setEmail("admin@example.com");
//			adminUser.addRole(roleAdmin);
//			userRepository.save(adminUser);
//		}

	}
}
