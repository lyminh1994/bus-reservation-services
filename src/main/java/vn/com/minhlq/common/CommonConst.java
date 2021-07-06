package vn.com.minhlq.common;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

/**
 * @author minhlq
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class CommonConst {

    public static final Integer ENABLE = 1;

    public static final Integer DISABLE = 0;

    public static final Integer PAGE = 1;

    public static final Integer BUTTON = 2;

    public static final String REDIS_JWT_KEY_PREFIX = "security:jwt:";

    public static final Integer DEFAULT_CURRENT_PAGE = 1;

    public static final Integer DEFAULT_PAGE_SIZE = 10;

    public static final String ANONYMOUS_NAME = "Anonymous";

}
