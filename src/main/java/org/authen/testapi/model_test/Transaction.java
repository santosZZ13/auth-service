package org.authen.testapi.model_test;

import lombok.Data;
import org.springframework.data.redis.core.RedisHash;
import org.springframework.data.redis.core.index.Indexed;

import javax.persistence.Id;
import java.util.Date;

@RedisHash("transaction")
@Data
public class Transaction {
	@Id
	private Long id;
	private int amount;
	private Date date;
	@Indexed
	private Long fromAccountId;
	@Indexed
	private Long toAccountId;
}
