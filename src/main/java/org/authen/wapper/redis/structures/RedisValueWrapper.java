package org.authen.wapper.redis.structures;

import org.authen.wapper.redis.RedisBaseWrapper;
import org.springframework.data.redis.connection.BitFieldSubCommands;
import org.springframework.data.redis.core.TimeoutUtils;

import java.time.Duration;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

public interface RedisValueWrapper<K, V> extends RedisBaseWrapper<K, V> {

	/**
	 * Set the value of the key
	 * Command in Redis: SET key value
	 * Time complexity: O(1)
	 *
	 * @param key   key
	 * @param value value
	 */
	default void set(K key, V value) {
	}

	/**
	 * <p>Set the time-out for the key.</p>
	 * <p>Command in Redis: SETEX key seconds value</p>
	 * <p>Time complexity: O(1)</p>
	 *
	 * @param key     key
	 * @param value   value
	 * @param timeOut time out value in milliseconds (ms)
	 * @param unit    time unit (TimeUnit) e.g. TimeUnit.SECONDS, TimeUnit.MILLISECONDS
	 */
	default void setTTL(K key, V value, long timeOut, TimeUnit unit) {
	}


	/**
	 * Set the {@code value} and expiration {@code timeout} for {@code key}.
	 *
	 * @param key     must not be {@literal null}.
	 * @param value   must not be {@literal null}.
	 * @param timeout must not be {@literal null}.
	 * @throws IllegalArgumentException if either {@code key}, {@code value} or {@code timeout} is not present.
	 * @see <a href="https://redis.io/commands/setex">Redis Documentation: SETEX</a>
	 * @since 2.1
	 */
	default void setTTL(K key, V value, Duration timeOut) {
		if (TimeoutUtils.hasMillis(timeOut)) {
			setTTL(key, value, timeOut.toMillis(), TimeUnit.MILLISECONDS);
		} else {
			setTTL(key, value, timeOut.getSeconds(), TimeUnit.SECONDS);
		}
	}

	/**
	 * Set {@code key} to hold the string {@code value} if {@code key} is absent.
	 *
	 * @param key   must not be {@literal null}.
	 * @param value must not be {@literal null}.
	 * @return {@literal null} when used in pipeline / transaction.
	 * @see <a href="https://redis.io/commands/setnx">Redis Documentation: SETNX</a>
	 */
	default Boolean setIfAbsent(K key, V value) {
		return null;
	}

	/**
	 * Set {@code key} to hold the string {@code value} and expiration {@code timeout} if {@code key} is absent.
	 *
	 * @param key     must not be {@literal null}.
	 * @param value   must not be {@literal null}.
	 * @param timeout the key expiration timeout.
	 * @param unit    must not be {@literal null}.
	 * @return {@literal null} when used in pipeline / transaction.
	 * @see <a href="https://redis.io/commands/set">Redis Documentation: SET</a>
	 * @since 2.1
	 */
	default Boolean setIfAbsent(K key, V value, long timeout, TimeUnit unit) {
		return null;
	}

	;

	/**
	 * Set {@code key} to hold the string {@code value} and expiration {@code timeout} if {@code key} is absent.
	 *
	 * @param key     must not be {@literal null}.
	 * @param value   must not be {@literal null}.
	 * @param timeout must not be {@literal null}.
	 * @return {@literal null} when used in pipeline / transaction.
	 * @throws IllegalArgumentException if either {@code key}, {@code value} or {@code timeout} is not present.
	 * @see <a href="https://redis.io/commands/set">Redis Documentation: SET</a>
	 * @since 2.1
	 */
	default Boolean setIfAbsent(K key, V value, Duration timeout) {
		if (Objects.nonNull(timeout) && TimeoutUtils.hasMillis(timeout)) {
			return setIfAbsent(key, value, timeout.toMillis(), TimeUnit.MILLISECONDS);
		}
		return setIfAbsent(key, value, timeout.toMillis(), TimeUnit.SECONDS);
	}

	/**
	 * Set multiple keys to multiple values using key-value pairs provided in {@code tuple}.
	 *
	 * @param map must not be {@literal null}.
	 * @see <a href="https://redis.io/commands/mset">Redis Documentation: MSET</a>
	 */
	default void multiSet(Map<? extends K, ? extends V> map) {
	}

	/**
	 * Set multiple keys to multiple values using key-value pairs provided in {@code tuple} only if the provided key does
	 * not exist.
	 *
	 * @param map must not be {@literal null}.
	 * @return {@literal null} when used in pipeline / transaction.
	 * @see <a href="https://redis.io/commands/msetnx">Redis Documentation: MSETNX</a>
	 */
	default Boolean multiSetIfAbsent(Map<? extends K, ? extends V> map) {
		return null;
	}

	/**
	 * Get the value of {@code key}.
	 *
	 * @param key must not be {@literal null}.
	 * @return {@literal null} when key does not exist or used in pipeline / transaction.
	 * @see <a href="https://redis.io/commands/get">Redis Documentation: GET</a>
	 */
	default V get(Object key) {
		return null;
	}

	/**
	 * Return the value at {@code key} and delete the key.
	 *
	 * @param key must not be {@literal null}.
	 * @return {@literal null} when key does not exist or used in pipeline / transaction.
	 * @see <a href="https://redis.io/commands/getdel">Redis Documentation: GETDEL</a>
	 * @since 2.6
	 */
	default V getAndDelete(Object key) {
		return null;
	}

	/**
	 * Return the value at {@code key} and expire the key by applying {@code timeout}.
	 *
	 * @param key     must not be {@literal null}.
	 * @param timeout
	 * @param unit    must not be {@literal null}.
	 * @return {@literal null} when key does not exist or used in pipeline / transaction.
	 * @see <a href="https://redis.io/commands/getex">Redis Documentation: GETEX</a>
	 * @since 2.6
	 */
	default V getAndExpire(K key, long timeout, TimeUnit unit) {
		return null;
	}

	;

	/**
	 * Return the value at {@code key} and expire the key by applying {@code timeout}.
	 *
	 * @param key     must not be {@literal null}.
	 * @param timeout must not be {@literal null}.
	 * @return {@literal null} when key does not exist or used in pipeline / transaction.
	 * @see <a href="https://redis.io/commands/getex">Redis Documentation: GETEX</a>
	 * @since 2.6
	 */
	default V getAndExpire(K key, Duration timeout) {
		if (Objects.nonNull(timeout) && TimeoutUtils.hasMillis(timeout)) {
			return getAndExpire(key, timeout.toMillis(), TimeUnit.MILLISECONDS);
		}
		return getAndExpire(key, timeout.toMillis(), TimeUnit.SECONDS);
	}

	//TODO
	default V getAndPersist(K key) {
		return null;
	};

	/**
	 * Set {@code value} of {@code key} and return its old value.
	 *
	 * @param key must not be {@literal null}.
	 * @return {@literal null} when key does not exist or used in pipeline / transaction.
	 * @see <a href="https://redis.io/commands/getset">Redis Documentation: GETSET</a>
	 */
	default V getAndSet(K key, V value) {
		return null;
	};

	/**
	 * Get multiple {@code keys}. Values are in the order of the requested keys Absent field values are represented using
	 * {@code null} in the resulting {@link List}.
	 *
	 * @param keys must not be {@literal null}.
	 * @return {@literal null} when used in pipeline / transaction.
	 * @see <a href="https://redis.io/commands/mget">Redis Documentation: MGET</a>
	 */
	default List<V> multiGet(Collection<K> keys) {
		return null;
	};

	/**
	 * Increment a floating point number value stored as string value under {@code key} by {@code delta}.
	 *
	 * @param key must not be {@literal null}.
	 * @param delta
	 * @return {@literal null} when used in pipeline / transaction.
	 * @see <a href="https://redis.io/commands/incrbyfloat">Redis Documentation: INCRBYFLOAT</a>
	 */
	default Double increment(K key, double delta) {
		return null;
	};

	/**
	 * Increment an integer value stored as string value under {@code key} by {@code delta}.
	 *
	 * @param key must not be {@literal null}.
	 * @param delta
	 * @return {@literal null} when used in pipeline / transaction.
	 * @see <a href="https://redis.io/commands/incrby">Redis Documentation: INCRBY</a>
	 */
	default Long increment(K key, long delta) {
		return null;
	};

	/**
	 * Decrement an integer value stored as string value under {@code key} by one.
	 *
	 * @param key must not be {@literal null}.
	 * @return {@literal null} when used in pipeline / transaction.
	 * @since 2.1
	 * @see <a href="https://redis.io/commands/decr">Redis Documentation: DECR</a>
	 */
	default Long decrement(K key) {
		return null;
	};

	/**
	 * Decrement an integer value stored as string value under {@code key} by {@code delta}.
	 *
	 * @param key must not be {@literal null}.
	 * @param delta
	 * @return {@literal null} when used in pipeline / transaction.
	 * @since 2.1
	 * @see <a href="https://redis.io/commands/decrby">Redis Documentation: DECRBY</a>
	 */
	default Long decrement(K key, long delta) {
		return null;
	}

	/**
	 * Append a {@code value} to {@code key}.
	 *
	 * @param key must not be {@literal null}.
	 * @param value
	 * @return {@literal null} when used in pipeline / transaction.
	 * @see <a href="https://redis.io/commands/append">Redis Documentation: APPEND</a>
	 */
	default Integer append(K key, String value) {
		return null;
	};

	/**
	 * Get a substring of value of {@code key} between {@code begin} and {@code end}.
	 *
	 * @param key must not be {@literal null}.
	 * @param start
	 * @param end
	 * @return {@literal null} when used in pipeline / transaction.
	 * @see <a href="https://redis.io/commands/getrange">Redis Documentation: GETRANGE</a>
	 */
	default String get(K key, long start, long end) {
		return null;
	}

	/**
	 * Overwrite parts of {@code key} starting at the specified {@code offset} with given {@code value}.
	 *
	 * @param key must not be {@literal null}.
	 * @param value
	 * @param offset
	 * @see <a href="https://redis.io/commands/setrange">Redis Documentation: SETRANGE</a>
	 */
	default void set(K key, V value, long offset) {};

	/**
	 * Get the length of the value stored at {@code key}.
	 *
	 * @param key must not be {@literal null}.
	 * @return {@literal null} when used in pipeline / transaction.
	 * @see <a href="https://redis.io/commands/strlen">Redis Documentation: STRLEN</a>
	 */
	default Long size(K key) {
		return null;
	};

	// TODO
	default Boolean setBit(K key, long offset, boolean value) {
		return null;
	};

	// TODO
	default Boolean getBit(K key, long offset) {
		return null;
	}

	// TODO
	default List<Long> bitField(K key, BitFieldSubCommands subCommands) {
		return null;
	}

}
