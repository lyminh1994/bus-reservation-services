package vn.com.minhlq.utils.core;

import lombok.experimental.UtilityClass;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;

/**
 * @author MinhLQ
 */
@UtilityClass
public class StringFormatter {

    public String format(String strPattern, Object... argArray) {
        if (!StringUtils.isBlank(strPattern) && !ArrayUtils.isEmpty(argArray)) {
            int strPatternLength = strPattern.length();
            StringBuilder stringBuilder = new StringBuilder(strPatternLength + 50);
            int handledPosition = 0;

            for (int argIndex = 0; argIndex < argArray.length; argIndex++) {
                int delimiterIndex = strPattern.indexOf("{}", handledPosition);
                if (delimiterIndex == -1) {
                    if (handledPosition == 0) {
                        return strPattern;
                    }

                    stringBuilder.append(strPattern, handledPosition, strPatternLength);
                    return stringBuilder.toString();
                }

                if (delimiterIndex > 0 && strPattern.charAt(delimiterIndex - 1) == '\\') {
                    if (delimiterIndex > 1 && strPattern.charAt(delimiterIndex - 2) == '\\') {
                        stringBuilder.append(strPattern, handledPosition, delimiterIndex - 1);
                        stringBuilder.append(StringUtil.utf8String(argArray[argIndex]));
                        handledPosition = delimiterIndex + 2;
                    } else {
                        argIndex--;
                        stringBuilder.append(strPattern, handledPosition, delimiterIndex - 1);
                        stringBuilder.append('{');
                        handledPosition = delimiterIndex + 1;
                    }
                } else {
                    stringBuilder.append(strPattern, handledPosition, delimiterIndex);
                    stringBuilder.append(StringUtil.utf8String(argArray[argIndex]));
                    handledPosition = delimiterIndex + 2;
                }
            }

            stringBuilder.append(strPattern, handledPosition, strPattern.length());
            return stringBuilder.toString();
        } else {
            return strPattern;
        }
    }

}
