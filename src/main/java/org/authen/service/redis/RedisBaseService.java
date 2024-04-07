package org.authen.service.redis;

public interface RedisBaseService {

	default void set(String key, String value) {}
	default void setTTL(String key, String value, long ttl) {}

}
