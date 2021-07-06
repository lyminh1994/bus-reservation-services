package vn.com.minhlq.utils;

import lombok.experimental.UtilityClass;

/**
 * @author minhlq
 */
@UtilityClass
public class CollectionUtil {

    public <T> T getFirst(Iterable<T> iterable) {
        return IterableUtil.getFirst(iterable);
    }

}
