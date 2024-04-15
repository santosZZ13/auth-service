package org.authen.testapi.model_test;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.data.redis.core.index.Indexed;

@Data
@AllArgsConstructor
public class Account {
	@Indexed
	private Long id;
	private String number;
	private int balance;
}
