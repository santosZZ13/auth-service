package org.authen.wapper.redis.structures;

import org.authen.wapper.redis.RedisBaseWrapper;
import org.springframework.data.redis.connection.RedisListCommands;
import org.springframework.data.redis.core.ListOperations;

import java.time.Duration;
import java.util.Collection;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * TODO: add operations for list
 * @param <K>
 * @param <V>
 */
public interface RedisListWrapper<K, V> extends RedisBaseWrapper<K, V> {

	//TODO
	default Long lPushAtPivot(K key, V pivot, V value) {
		return null;
	}

	//TODO
	default void trim(K key, long start, long end) {
	}

	//TODO
	default Long rPushAtPivot(K key, V pivot, V value) {
		return null;
	}


	/**
	 * Get elements between {@code begin} and {@code end} from list at {@code key}.
	 *
	 * @param key must not be {@literal null}.
	 * @param start
	 * @param end
	 * @return {@literal null} when used in pipeline / transaction.
	 * @see <a href="https://redis.io/commands/lrange">Redis Documentation: LRANGE</a>
	 */
	default List<V> range(K key, long start, long end) {
		return null;
	}

	/**
	 * Get the size of list stored at {@code key}.
	 *
	 * @param key must not be {@literal null}.
	 * @return {@literal null} when used in pipeline / transaction.
	 * @see <a href="https://redis.io/commands/llen">Redis Documentation: LLEN</a>
	 */
	default Long size(K key) {
		return null;
	}

	/**
	 * Prepend {@code values} to {@code key}.
	 *
	 * @param key must not be {@literal null}.
	 * @return {@literal null} when used in pipeline / transaction.
	 * @see <a href="https://redis.io/commands/lpush">Redis Documentation: LPUSH</a>
	 */
	default Long lPush(K key, V value) {
		return null;
	}

	/**
	 * Prepend {@code values} to {@code key}.
	 *
	 * @param key must not be {@literal null}.
	 * @param values
	 * @return {@literal null} when used in pipeline / transaction.
	 * @see <a href="https://redis.io/commands/lpush">Redis Documentation: LPUSH</a>
	 */
	default Long lPush(K key, V... values) {
		return null;
	}

	/**
	 * Prepend {@code values} to {@code key}.
	 *
	 * @param key must not be {@literal null}.
	 * @param values
	 * @return {@literal null} when used in pipeline / transaction.
	 * @see <a href="https://redis.io/commands/lpush">Redis Documentation: LPUSH</a>
	 */
	default Long lPush(K key, Collection<V> values) {
		return null;
	}

	/**
	 * Prepend {@code values} to {@code key} only if the list exists.
	 *
	 * @param key must not be {@literal null}.
	 * @param value
	 * @return {@literal null} when used in pipeline / transaction.
	 * @see <a href="https://redis.io/commands/lpushx">Redis Documentation: LPUSHX</a>
	 */
	default Long lPushIfPresent(K key, V value) {
		return null;
	}

	/**
	 * Append {@code value} to {@code key}.
	 *
	 * @param key must not be {@literal null}.
	 * @param value
	 * @return {@literal null} when used in pipeline / transaction.
	 * @see <a href="https://redis.io/commands/rpush">Redis Documentation: RPUSH</a>
	 */
	default Long rPush(K key, V value) {
		return null;
	}


	/**
	 * Append {@code value} to {@code key}.
	 *
	 * @param key must not be {@literal null}.
	 * @param values
	 * @return {@literal null} when used in pipeline / transaction.
	 * @see <a href="https://redis.io/commands/rpush">Redis Documentation: RPUSH</a>
	 */
	default Long rPush(K key, V... values) {
		return null;
	}


	/**
	 * Append {@code value} to {@code key}.
	 *
	 * @param key must not be {@literal null}.
	 * @param values
	 * @return {@literal null} when used in pipeline / transaction.
	 * @see <a href="https://redis.io/commands/rpush">Redis Documentation: RPUSH</a>
	 */
	default Long rPush(K key, Collection<V> values) {
		return null;
	}

	/**
	 * Append {@code values} to {@code key} only if the list exists.
	 *
	 * @param key must not be {@literal null}.
	 * @param value
	 * @return {@literal null} when used in pipeline / transaction.
	 * @see <a href="https://redis.io/commands/rpushx">Redis Documentation: RPUSHX</a>
	 */
	default Long rPushIfPresent(K key, V value) {
		return null;
	}

	/**
	 * List insertion position.
	 */
	enum Position {
		BEFORE, AFTER;
	}

	/**
	 * List move direction.
	 *
	 * @since 2.6
	 */
	enum Direction {
		LEFT, RIGHT;
		/**
		 * Alias for {@link RedisListCommands.Direction#LEFT}.
		 *
		 * @return
		 */
		public static Direction first() {
			return LEFT;
		}

		/**
		 * Alias for {@link RedisListCommands.Direction#RIGHT}.
		 *
		 * @return
		 */
		public static Direction last() {
			return RIGHT;
		}
	}

	class MoveFromWrapper<K> {
		final K key;
		final Direction direction;

		public MoveFromWrapper(K key, Direction direction) {
			this.key = key;
			this.direction = direction;
		}
	}
	class MoveToWrapper<K> {
		final K key;
		final Direction direction;

		public MoveToWrapper(K key, Direction direction) {
			this.key = key;
			this.direction = direction;
		}
	}

	// TODO
	default V move(MoveFromWrapper<K> from, MoveToWrapper<K> to) {
		return null;
	}

	// TODO
	default V move(K sourceKey, RedisListCommands.Direction from, K destinationKey, RedisListCommands.Direction to) {
		return null;
	};

	//TODO
	default V move(ListOperations.MoveFrom<K> from, ListOperations.MoveTo<K> to, Duration timeout) {
		return null;
	}

	//TODO
	default V move(K sourceKey, RedisListCommands.Direction from, K destinationKey, RedisListCommands.Direction to, Duration timeout) {
		return null;
	}

	default V move(K sourceKey, RedisListCommands.Direction from, K destinationKey, RedisListCommands.Direction to, long timeout, TimeUnit unit) {
		return null;
	};


}
