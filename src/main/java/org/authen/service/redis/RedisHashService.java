package org.authen.service.redis;

public interface RedisHashService extends RedisBaseService {
	default void hashSet(String key, String hashKey, Object hashValue) {}
	default boolean hashExists(String key, String hashKey){return false;}
}
