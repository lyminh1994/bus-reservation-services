package vn.com.minhlq.utils.core;

import lombok.experimental.UtilityClass;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.CharSetUtils;
import org.apache.commons.lang3.StringUtils;

import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.charset.UnsupportedCharsetException;

/**
 * @author MinhLQ
 */
@Slf4j
@UtilityClass
public class CharsetUtil extends CharSetUtils {

    public final String ISO_8859_1 = "ISO-8859-1";
    public final String UTF_8 = "UTF-8";
    public final String GBK = "GBK";
    public final Charset CHARSET_ISO_8859_1;
    public final Charset CHARSET_UTF_8;
    public final Charset CHARSET_GBK;

    public Charset charset(String charsetName) throws UnsupportedCharsetException {
        return StringUtils.isBlank(charsetName) ? Charset.defaultCharset() : Charset.forName(charsetName);
    }

    public Charset parse(String charsetName) {
        return parse(charsetName, Charset.defaultCharset());
    }

    public Charset parse(String charsetName, Charset defaultCharset) {
        if (StringUtils.isBlank(charsetName)) {
            return defaultCharset;
        } else {
            Charset result;
            try {
                result = Charset.forName(charsetName);
            } catch (UnsupportedCharsetException var4) {
                result = defaultCharset;
            }

            return result;
        }
    }

    public String convert(String source, String srcCharset, String destCharset) {
        return convert(source, Charset.forName(srcCharset), Charset.forName(destCharset));
    }

    public String convert(String source, Charset srcCharset, Charset destCharset) {
        if (null == srcCharset) {
            srcCharset = StandardCharsets.ISO_8859_1;
        }

        if (null == destCharset) {
            destCharset = StandardCharsets.UTF_8;
        }

        return !StringUtils.isBlank(source) && !srcCharset.equals(destCharset) ? new String(source.getBytes(srcCharset), destCharset) : source;
    }

    public String defaultCharsetName() {
        return defaultCharset().name();
    }

    public Charset defaultCharset() {
        return Charset.defaultCharset();
    }

    static {
        CHARSET_ISO_8859_1 = StandardCharsets.ISO_8859_1;
        CHARSET_UTF_8 = StandardCharsets.UTF_8;
        Charset charsetGbk = null;

        try {
            charsetGbk = Charset.forName("GBK");
        } catch (UnsupportedCharsetException ex) {
            log.error("###CharsetUtil###", ex);
        }

        CHARSET_GBK = charsetGbk;
    }

}
