package com.fengxf.spring_boot.config;

import java.lang.reflect.Method;

import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.CachingConfigurerSupport;
import org.springframework.cache.interceptor.KeyGenerator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.data.redis.serializer.SerializationException;
import org.springframework.data.redis.serializer.StringRedisSerializer;

import com.fasterxml.jackson.annotation.JsonTypeInfo.As;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectMapper.DefaultTyping;

@Configuration
public class RedisTemplateConfig extends CachingConfigurerSupport {

	/**
	 * 缓存 key 默认的生成方式
	 */
	@Bean
	public KeyGenerator keyGenerator() {
		return new KeyGenerator() {
			@Override
			public Object generate(Object target, Method method, Object... params) {
				StringBuilder sb = new StringBuilder();
				sb.append(target.getClass().getName());
				sb.append("_" + method.getName());
				for (Object obj : params) {
					if (obj != null) {
						sb.append("_" + obj.toString());
					}
				}
				
				return sb.toString();
			}
		};
	}

	@Bean
	public CacheManager cacheManager(RedisTemplate<String, Object> redisTemplate) {
		return new RedisCacheManager(redisTemplate);
	}

	@Bean
	RedisTemplate<String, Object> redisTemplate(RedisConnectionFactory rcf) {
		RedisTemplate<String, Object> template = new RedisTemplate<>();
		template.setConnectionFactory(rcf);
		template.setKeySerializer(new StringRedisSerializer());
		template.setValueSerializer(new JsonRedisSerializer());
		template.afterPropertiesSet();

		return template;
	}

	static class JsonRedisSerializer implements RedisSerializer<Object> {
		private final ObjectMapper om;

		public JsonRedisSerializer() {
			this.om = new ObjectMapper().enableDefaultTyping(DefaultTyping.NON_FINAL, As.PROPERTY);
		}

		@Override
		public byte[] serialize(Object t) throws SerializationException {
			try {
				return om.writeValueAsBytes(t);
			} catch (JsonProcessingException e) {
				throw new SerializationException(e.getMessage(), e);
			}
		}

		@Override
		public Object deserialize(byte[] bytes) throws SerializationException {
			if (bytes == null) {
				return null;
			}

			try {
				return om.readValue(bytes, Object.class);
			} catch (Exception e) {
				throw new SerializationException(e.getMessage(), e);
			}
		}
	}
}
