package vn.com.minhlq;

import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import vn.com.minhlq.common.CommonConst;
import vn.com.minhlq.common.PageResult;
import vn.com.minhlq.utils.RedisUtil;
import vn.com.minhlq.utils.core.JSONUtil;

@Slf4j
class RedisUtilTest extends BoilerplateApplicationTests {

    @Autowired
    private RedisUtil redisUtil;

    @Test
    void findKeysForPage() {
        PageResult pageResult = redisUtil.findKeysForPage(CommonConst.REDIS_JWT_KEY_PREFIX + CommonConst.SYMBOL_STAR, 2, 1);
        log.info("【pageResult】= {}", JSONUtil.toJsonStr(pageResult));
    }

}
