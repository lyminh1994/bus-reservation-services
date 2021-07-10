package vn.com.minhlq.utils;

import lombok.RequiredArgsConstructor;

import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

import java.util.Collection;

/**
 * @author minhlq
 */
@Component
@RequiredArgsConstructor
public class RedisUtils {

    private final StringRedisTemplate stringRedisTemplate;

    /**
     * Delete a key in Redis
     *
     * @param key Key
     */
    public void delete(String key) {
        stringRedisTemplate.delete(key);
    }

    /**
     * Delete some keys in Redis in batch
     *
     * @param keys List key
     */
    public void delete(Collection<String> keys) {
        stringRedisTemplate.delete(keys);
    }

}
