package org.authen.testapi;

import org.authen.testapi.modeltest.Customer;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface CustomerRepository extends CrudRepository<Customer, Long> {
	Customer findByExternalId(String externalId);
	List<Customer> findByAccountsId(Long id);
}
