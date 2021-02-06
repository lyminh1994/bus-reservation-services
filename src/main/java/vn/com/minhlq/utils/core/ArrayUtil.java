package vn.com.minhlq.utils.core;

import lombok.experimental.UtilityClass;
import org.apache.commons.lang3.ArrayUtils;

/**
 * @author Minh Ly Quang
 */
@UtilityClass
public class ArrayUtil extends ArrayUtils {

    public boolean isArray(Object obj) {
        return null != obj && obj.getClass().isArray();
    }

}
