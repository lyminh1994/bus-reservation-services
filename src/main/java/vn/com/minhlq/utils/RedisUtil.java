package vn.com.minhlq.utils;

import com.google.common.collect.Lists;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.Cursor;
import org.springframework.data.redis.core.RedisConnectionUtils;
import org.springframework.data.redis.core.ScanOptions;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;
import vn.com.minhlq.commons.PageResult;

import java.util.Collection;
import java.util.List;

/**
 * @author MinhLQ
 */
@Slf4j
@Component
@AllArgsConstructor
public class RedisUtil {

    private final StringRedisTemplate stringRedisTemplate;

    /**
     * Paging to obtain the specified format key, use the scan command instead of the keys command, which can improve the query efficiency in the case of a large amount of data
     *
     * @param patternKey  Key format
     * @param currentPage Current page number
     * @param pageSize    Number of items per page
     * @return Paging to get the specified format key
     */
    public PageResult<String> findKeysForPage(String patternKey, int currentPage, int pageSize) {
        ScanOptions options = ScanOptions.scanOptions().match(patternKey).build();
        RedisConnectionFactory factory = stringRedisTemplate.getConnectionFactory();
        RedisConnection rc = factory.getConnection();
        Cursor<byte[]> cursor = rc.scan(options);

        List<String> result = Lists.newArrayList();

        long tmpIndex = 0;
        int startIndex = (currentPage - 1) * pageSize;
        int end = currentPage * pageSize;
        while (cursor.hasNext()) {
            String key = new String(cursor.next());
            if (tmpIndex >= startIndex && tmpIndex < end) {
                result.add(key);
            }
            tmpIndex++;
        }

        try {
            cursor.close();
            RedisConnectionUtils.releaseConnection(rc, factory, Boolean.TRUE);
        } catch (Exception e) {
            log.warn("Redis connection closed abnormally，", e);
        }

        return new PageResult<>(result, tmpIndex);
    }

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
