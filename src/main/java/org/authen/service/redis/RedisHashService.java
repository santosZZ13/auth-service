package org.authen.service.redis;

public interface RedisHashService<K, V> extends RedisBaseService<K, V> {
	default void hashSet(K key, V hashKey, Object hashValue) {}
	default boolean hashExists(K key, V hashKey) {return false;}
}
