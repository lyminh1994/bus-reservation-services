package vn.com.minhlq.exception;

import lombok.Data;
import lombok.EqualsAndHashCode;
import vn.com.minhlq.common.BaseException;
import vn.com.minhlq.common.ResultCode;

/**
 * @author MinhLQ
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class SecurityException extends BaseException {

    public SecurityException(ResultCode resultCode) {
        super(resultCode);
    }

    public SecurityException(ResultCode resultCode, Object data) {
        super(resultCode, data);
    }

    public SecurityException(Integer code, String message) {
        super(code, message);
    }

    public SecurityException(Integer code, String message, Object data) {
        super(code, message, data);
    }

}
