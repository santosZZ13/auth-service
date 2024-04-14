package org.authen.util;

import lombok.AllArgsConstructor;
import lombok.NonNull;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.hash.HashMapper;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.Objects;


@Component
@AllArgsConstructor
public class HashMapping<T> {
	@NonNull
	private final RedisTemplate<String, T> redisTemplate;
	@NonNull
	private final HashMapper<Object, byte[], byte[]> hashMapper;
	private final StringRedisTemplate stringRedisTemplate;

	public void writeHash(String key, T t) {
//		checkNotNull(key, "hash key cannot be null");
//		checkNotNull(t, "hash value cannot be null");
		stringRedisTemplate.execute((RedisCallback<Void>) connection -> {
			Map<byte[], byte[]> mappedHash = hashMapper.toHash(t);
			connection.hMSet(key.getBytes(), mappedHash);
			return null;
		});
	}

	public T loadHash(String key) {
//		checkNotNull(key, "hash key cannot be null");
		Map<byte[], byte[]> loadedHash = Objects.requireNonNull(stringRedisTemplate.getConnectionFactory())
						.getConnection().hGetAll(key.getBytes());
		return (T) hashMapper.fromHash(loadedHash);
	}

	private void checkNotNull(Object key, String msg) {

	}


}
