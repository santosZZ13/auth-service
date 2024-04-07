package org.authen.service.redis;

import lombok.AllArgsConstructor;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class RedisServiceImpl implements RedisImpl {

	private final RedisTemplate<String, Object> redisTemplate;
	private final ValueOperations<String, Object> valueOperations = redisTemplate.opsForValue();
	private final HashOperations<String, String, Object> hashOperations = redisTemplate.opsForHash();


	@Override
	public void set(String key, String value) {
		valueOperations.set(key, value);
	}

	@Override
	public void setTTL(String key, String value, long ttl) {
		valueOperations.set(key, value, ttl);
	}
}
