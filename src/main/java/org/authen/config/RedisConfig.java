package org.authen.config;


import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.hash.HashMapper;
import org.springframework.data.redis.hash.ObjectHashMapper;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializationContext;
import org.springframework.data.redis.serializer.StringRedisSerializer;

import java.time.Duration;

@EnableCaching
@Configuration
public class RedisConfig {

	@Value("${spring.redis.host}")
	private String redisHost;

	@Value("${spring.redis.port}")
	private int redisPort;

	@Value("${spring.redis.user_expiration_second}")
	private long userExpirationSecond;

	@Value("${spring.redis.access_token_expiration_second}")
	private long accessTokenExpirationSecond;

	@Value("${spring.redis.refresh_token_expiration_second}")
	private long refreshTokenExpirationSecond;

	@Value("${spring.redis.activate_token_expiration_second}")
	private long activateTokenExpirationSecond;

	@Value("${spring.redis.default_expiration_second}")
	private long defaultExpirationSecond;

	public static final String USER = "user";

	public static final String ACCESS_TOKEN = "access_token";

	public static final String REFRESH_TOKEN = "refresh_token";

	public static final String ACTIVATE_TOKEN = "activate_token";

	@Bean
	public RedisConnectionFactory redisConnectionFactory() {
		RedisStandaloneConfiguration redisStandaloneConfiguration = new RedisStandaloneConfiguration(redisHost, redisPort);
		// redisStandaloneConfiguration.setPassword(RedisPassword.of("yourRedisPasswordIfAny"));
		return new LettuceConnectionFactory(redisStandaloneConfiguration);
	}

//	@Bean
//	public RedisCacheManager redisCacheManager(RedisConnectionFactory redisConnectionFactory,
//											   RedisTemplate<String, Object> redisTemplate) {
//		return RedisCacheManager.builder(redisConnectionFactory)
//				.withCacheConfiguration(USER, customSerializerRedisConfig(redisTemplate))
//				.withCacheConfiguration(ACCESS_TOKEN, accessTokenSerializerRedisConfig())
//				.withCacheConfiguration(REFRESH_TOKEN, refreshTokenSerializerRedisConfig())
//				.withCacheConfiguration(ACTIVATE_TOKEN, activateTokenSerializerRedisConfig())
//				.cacheDefaults(RedisCacheConfiguration.defaultCacheConfig()
//						.entryTtl(Duration.ofSeconds(defaultExpirationSecond)))
//				.transactionAware().build();
//	}


	@Bean
	public RedisCacheManager redisCacheManager(RedisConnectionFactory redisConnectionFactory) {
		return RedisCacheManager.create(redisConnectionFactory);
	}

	@Bean
	public RedisTemplate<String, Object> redisTemplate(RedisConnectionFactory redisConnectionFactory) {
		RedisTemplate<String, Object> redisTemplate = new RedisTemplate<>();

		redisTemplate.setConnectionFactory(redisConnectionFactory);
		redisTemplate.setKeySerializer(new StringRedisSerializer());
		redisTemplate.setValueSerializer(new GenericJackson2JsonRedisSerializer());
		redisTemplate.setHashKeySerializer(new StringRedisSerializer());
		redisTemplate.setHashValueSerializer(new GenericJackson2JsonRedisSerializer());

		return redisTemplate;
	}


	@Bean
	public HashMapper<Object, byte[], byte[]> hashMapper() {
		return new ObjectHashMapper();
	}

//	public RedisCacheConfiguration customSerializerRedisConfig(RedisTemplate<String, Object> redisTemplate) {
//		return RedisCacheConfiguration.defaultCacheConfig().entryTtl(Duration.ofSeconds(userExpirationSecond))
//				.serializeValuesWith(
//						RedisSerializationContext.SerializationPair.fromSerializer(redisTemplate.getValueSerializer()));
//	}
//
//	public RedisCacheConfiguration accessTokenSerializerRedisConfig() {
//		return RedisCacheConfiguration.defaultCacheConfig().entryTtl(Duration.ofSeconds(accessTokenExpirationSecond));
//	}
//
//	public RedisCacheConfiguration refreshTokenSerializerRedisConfig() {
//		return RedisCacheConfiguration.defaultCacheConfig().entryTtl(Duration.ofSeconds(refreshTokenExpirationSecond));
//	}
//
//	public RedisCacheConfiguration activateTokenSerializerRedisConfig() {
//		return RedisCacheConfiguration.defaultCacheConfig().entryTtl(Duration.ofSeconds(activateTokenExpirationSecond));
//	}
}
