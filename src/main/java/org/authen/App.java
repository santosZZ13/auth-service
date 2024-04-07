package org.authen;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class App {
	public static void main(String[] args) {
		ConfigurableApplicationContext applicationContext = SpringApplication.run(App.class, args);
//		RedisTemplate<String, Object> redisTemplate = applicationContext.getBean("redisTemplate", RedisTemplate.class);
//		redisTemplate.opsForValue().set("name", "John Doe");
	}
}
