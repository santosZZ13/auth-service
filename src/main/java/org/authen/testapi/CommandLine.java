package org.authen.testapi;

import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.authen.wapper.redis.RedisWrapper;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
@Log4j2
public class CommandLine implements CommandLineRunner {

	private final RedisWrapper redisWrapper;

	@Override
	public void run(String... args) throws Exception {
		log.info("Set value to redis");
		redisWrapper.increment("test:3", 10);
	}
}
