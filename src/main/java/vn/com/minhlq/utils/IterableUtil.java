package vn.com.minhlq.utils;

import lombok.experimental.UtilityClass;
import org.apache.commons.collections4.IterableUtils;

import java.util.Iterator;

/**
 * @author minhlq
 */
@UtilityClass
public class IterableUtil extends IterableUtils {

    public <T> T getFirst(Iterator<T> iterator) {
        return null != iterator && iterator.hasNext() ? iterator.next() : null;
    }

    public <T> T getFirst(Iterable<T> iterable) {
        return null == iterable ? null : getFirst(iterable.iterator());
    }

}
