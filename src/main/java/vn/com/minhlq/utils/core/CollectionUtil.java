package vn.com.minhlq.utils.core;

import lombok.experimental.UtilityClass;

/**
 * @author Minh Ly Quang
 */
@UtilityClass
public class CollectionUtil {

    public <T> T getFirst(Iterable<T> iterable) {
        return IterableUtil.getFirst(iterable);
    }

}
