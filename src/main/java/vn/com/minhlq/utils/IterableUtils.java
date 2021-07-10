package vn.com.minhlq.utils;

import java.util.Iterator;

import lombok.experimental.UtilityClass;

/**
 * @author minhlq
 */
@UtilityClass
public class IterableUtils extends org.apache.commons.collections4.IterableUtils {

    public <T> T getFirst(Iterator<T> iterator) {
        return null != iterator && iterator.hasNext() ? iterator.next() : null;
    }

    public <T> T getFirst(Iterable<T> iterable) {
        return null == iterable ? null : getFirst(iterable.iterator());
    }

}
