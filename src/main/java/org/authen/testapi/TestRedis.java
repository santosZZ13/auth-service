package org.authen.testapi;

import org.authen.testapi.modeltest.Customer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@Order(1)
public class TestRedis implements CommandLineRunner {

	@Autowired
	CustomerRepository repository;

	@Override
	public void run(String... args) throws Exception {
//		Customer customer = new Customer(1L, "80010121098", "John Smith");
//		customer.addAccount(new Account(1L, "1234567890", 2000));
//		customer.addAccount(new Account(2L, "1234567891", 4000));
//		customer.addAccount(new Account(3L, "1234567892", 6000));
//		customer = repository.save(customer);
//		System.out.println("Customer saved: " + customer);
		List<Customer> byAccountsId = repository.findByAccountsId(1L);
		System.out.println();
	}
}
