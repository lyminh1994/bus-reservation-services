package vn.com.minhlq;

import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import vn.com.minhlq.commons.Const;
import vn.com.minhlq.commons.PageResult;
import vn.com.minhlq.utils.RedisUtil;
import vn.com.minhlq.utils.core.JSONUtil;

@Slf4j
class RedisUtilTest extends BoilerplateApplicationTests {

    @Autowired
    private RedisUtil redisUtil;

    @Test
    void findKeysForPage() {
        PageResult pageResult = redisUtil.findKeysForPage(Const.REDIS_JWT_KEY_PREFIX + Const.SYMBOL_STAR, 2, 1);
        log.info("【pageResult】= {}", JSONUtil.toJsonStr(pageResult));
    }

}
