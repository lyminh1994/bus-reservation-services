package vn.com.minhlq.utils;

import lombok.experimental.UtilityClass;

/**
 * @author minhlq
 */
@UtilityClass
public class CollectionUtils {

    public <T> T getFirst(Iterable<T> iterable) {
        return IterableUtils.getFirst(iterable);
    }

}
