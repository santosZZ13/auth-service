package org.authen.service.redis;

public interface RedisImpl<K, V> extends RedisHashService<K, V>, RedisValueService<K, V> {
}
