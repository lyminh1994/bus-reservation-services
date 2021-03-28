package vn.com.minhlq.utils.core;

import lombok.experimental.UtilityClass;
import org.apache.commons.lang3.time.DateUtils;

/**
 * @author MinhLQ
 */
@UtilityClass
public class DateUtil extends DateUtils {

    public static long current(boolean isNano) {
        return isNano ? System.nanoTime() : System.currentTimeMillis();
    }

}
