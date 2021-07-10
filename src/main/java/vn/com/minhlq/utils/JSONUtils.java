package vn.com.minhlq.utils;

import com.google.gson.Gson;
import lombok.experimental.UtilityClass;
import org.apache.commons.lang3.StringUtils;

/**
 * @author minhlq
 */
@UtilityClass
public class JSONUtils {

    public String toJsonStr(Object json) {
        return null == json ? StringUtils.EMPTY : new Gson().toJson(json);
    }

}
