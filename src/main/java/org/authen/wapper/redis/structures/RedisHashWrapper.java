package org.authen.wapper.redis.structures;

import org.authen.wapper.redis.RedisBaseWrapper;

public interface RedisHashWrapper<K, V> extends RedisBaseWrapper<K, V> {
	default void hashSet(K key, V hashKey, Object hashValue) {}
	default boolean hashExists(K key, V hashKey) {return false;}
}
