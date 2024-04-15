package org.authen.testapi.repo_test;

import org.authen.testapi.model_test.Customer;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface CustomerRepository extends CrudRepository<Customer, Long> {
	Customer findByExternalId(String externalId);
	@Cacheable("accounts")
	List<Customer> findByAccountsId(Long id);
}
