package org.authen.testapi.repo_test;

import org.authen.testapi.model_test.User;
import org.jetbrains.annotations.NotNull;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepository extends CrudRepository<User, String> {
	User findFirstByEmail(String email);

}