package org.authen.service.redis;

import java.util.concurrent.TimeUnit;

public interface RedisValueService<K, V> extends RedisBaseService<K, V> {
	default void set(K key, V value) {}
	default void setTTL(K key, V value, long timeOut, TimeUnit unit) {}
}
