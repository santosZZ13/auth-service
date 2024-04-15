package org.authen.testapi.repo_test;

import org.authen.testapi.model_test.Role;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleRepository extends CrudRepository<Role, String> {
	Role findFirstByName(String name);

	Role findByName(String name);
}