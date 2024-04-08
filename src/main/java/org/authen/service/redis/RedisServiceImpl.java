package org.authen.service.redis;

import lombok.AllArgsConstructor;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

@Service
@AllArgsConstructor
public class RedisServiceImpl implements RedisImpl<String, Object> {

	private final RedisTemplate<String, Object> redisTemplate;
	private final ValueOperations<String, Object> valueOperations = redisTemplate.opsForValue();
	private final HashOperations<String, String, Object> hashOperations = redisTemplate.opsForHash();

	/**
	 * Set the value of the key
	 * Command in Redis: SET key value
	 * Time complexity: O(1)
	 * @param key key
	 * @param value value
	 */
	@Override
	public void set(String key, Object value) {
		valueOperations.set(key, value);
	}

	/**
	 * Set the time-out for the key
	 * Command in Redis: SET key value [EX seconds] [PX milliseconds] [NX|XX]
	 * Time complexity: O(1)
	 * @param key key
	 * @param value value
	 * @param timeOut time out value in milliseconds (ms)
	 * @param unit time unit (TimeUnit) e.g. TimeUnit.SECONDS, TimeUnit.MILLISECONDS
	 */
	@Override
	public void setTTL(String key, Object value, long timeOut, TimeUnit unit) {
		valueOperations.set(key, value, timeOut, unit);
	}


}
