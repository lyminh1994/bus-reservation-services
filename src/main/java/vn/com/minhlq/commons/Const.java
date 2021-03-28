package vn.com.minhlq.commons;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

/**
 * @author MinhLQ
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class Const {

    public static final Integer ENABLE = 1;

    public static final Integer DISABLE = 0;

    public static final Integer PAGE = 1;

    public static final Integer BUTTON = 2;

    public static final String REDIS_JWT_KEY_PREFIX = "security:jwt:";

    public static final String SYMBOL_STAR = "*";

    public static final String SYMBOL_EMAIL = "@";

    public static final Integer DEFAULT_CURRENT_PAGE = 1;

    public static final Integer DEFAULT_PAGE_SIZE = 10;

    public static final String ANONYMOUS_NAME = "anonymous";

}
