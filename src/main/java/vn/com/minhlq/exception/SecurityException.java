package vn.com.minhlq.exception;

import lombok.Data;
import lombok.EqualsAndHashCode;
import vn.com.minhlq.commons.BaseException;
import vn.com.minhlq.commons.Status;

/**
 * @author MinhLQ
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class SecurityException extends BaseException {

    public SecurityException(Status status) {
        super(status);
    }

    public SecurityException(Status status, Object data) {
        super(status, data);
    }

    public SecurityException(Integer code, String message) {
        super(code, message);
    }

    public SecurityException(Integer code, String message, Object data) {
        super(code, message, data);
    }

}
