package vn.com.minhlq.utils.core;

import lombok.experimental.UtilityClass;
import org.apache.commons.lang3.StringUtils;

import java.nio.ByteBuffer;
import java.nio.charset.Charset;

/**
 * @author Minh Ly Quang
 */
@UtilityClass
public class StringUtil extends StringUtils {

    public String toString(CharSequence cs) {
        return null == cs ? null : cs.toString();
    }

    public String toString(Object obj, Charset charset) {
        if (null == obj) {
            return null;
        } else if (obj instanceof String) {
            return (String) obj;
        } else if (obj instanceof byte[]) {
            return toString(obj, charset);
        } else if (obj instanceof Byte[]) {
            return toString(obj, charset);
        } else if (obj instanceof ByteBuffer) {
            return toString(obj, charset);
        } else {
            return ArrayUtil.isArray(obj) ? ArrayUtil.toString(obj) : obj.toString();
        }
    }

    public String replace(CharSequence str, int startInclude, int endExclude, char replacedChar) {
        if (isEmpty(str)) {
            return toString(str);
        } else {
            int strLength = str.length();
            if (startInclude > strLength) {
                return toString(str);
            } else {
                if (endExclude > strLength) {
                    endExclude = strLength;
                }

                if (startInclude > endExclude) {
                    return toString(str);
                } else {
                    char[] chars = new char[strLength];

                    for (int i = 0; i < strLength; ++i) {
                        if (i >= startInclude && i < endExclude) {
                            chars[i] = replacedChar;
                        } else {
                            chars[i] = str.charAt(i);
                        }
                    }

                    return new String(chars);
                }
            }
        }
    }

    public String hide(CharSequence str, int startInclude, int endExclude) {
        return replace(str, startInclude, endExclude, '*');
    }

    public String utf8String(Object obj) {
        return toString(obj, CharsetUtil.CHARSET_UTF_8);
    }

}
