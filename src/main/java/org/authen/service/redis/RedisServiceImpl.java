package org.authen.service.redis;

import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.concurrent.TimeUnit;



@Service
//@AllArgsConstructor
public class RedisServiceImpl implements RedisImpl<String, Object> {

	private final RedisTemplate<String, Object> redisTemplate;
	private final ValueOperations<String, Object> valueOperations;
	private final HashOperations<String, String, Object> hashOperations;

	public RedisServiceImpl(RedisTemplate<String, Object> redisTemplate) {
		this.redisTemplate = redisTemplate;
		this.hashOperations = redisTemplate.opsForHash();
		this.valueOperations = redisTemplate.opsForValue();

	}


	@Override
	public void set(String key, Object value) {
		valueOperations.set(key, value);
	}


	@Override
	public void setTTL(String key, Object value, long timeOut, TimeUnit unit) {
		valueOperations.set(key, value, timeOut, unit);
	}


	@Override
	public Boolean setIfAbsent(String key, Object value) {
		return valueOperations.setIfAbsent(key, value);
	}

	@Override
	public Boolean setIfAbsent(String key, Object value, long timeout, TimeUnit unit) {
		return valueOperations.setIfAbsent(key, value, timeout, unit);
	}

	@Override
	public void multiSet(Map<? extends String, ?> map) {
		valueOperations.multiSet(map);
	}

	@Override
	public Boolean multiSetIfAbsent(Map<? extends String, ?> map) {
		return valueOperations.multiSetIfAbsent(map);
	}

	@Override
	public Double increment(String key, double delta) {
		return valueOperations.increment(key, delta);
	}
}
