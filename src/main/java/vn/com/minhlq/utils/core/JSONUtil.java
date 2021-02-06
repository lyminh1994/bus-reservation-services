package vn.com.minhlq.utils.core;

import com.google.gson.Gson;
import lombok.experimental.UtilityClass;
import org.apache.commons.lang3.StringUtils;

/**
 * @author Minh Ly Quang
 */
@UtilityClass
public class JSONUtil {

    public String toJsonStr(Object json) {
        return null == json ? StringUtils.EMPTY : new Gson().toJson(json);
    }

}
