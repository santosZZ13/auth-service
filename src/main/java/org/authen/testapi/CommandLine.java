package org.authen.testapi;

import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.authen.service.redis.RedisImpl;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
@Log4j2
public class CommandLine implements CommandLineRunner {

	private final RedisImpl redisImpl;

	@Override
	public void run(String... args) throws Exception {
		log.info("Set value to redis");
		redisImpl.increment("test:3", 10);
	}
}
