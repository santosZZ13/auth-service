package org.authen.wapper.redis;

import org.authen.wapper.redis.structures.RedisHashWrapper;
import org.authen.wapper.redis.structures.RedisListWrapper;
import org.authen.wapper.redis.structures.RedisValueWrapper;

public interface RedisWrapper<K, V> extends RedisHashWrapper<K, V>, RedisValueWrapper<K, V>,
		RedisListWrapper<K, V>{
}
