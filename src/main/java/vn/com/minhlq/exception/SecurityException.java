package vn.com.minhlq.exception;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.http.HttpStatus;

/**
 * @author Minh Ly Quang
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class SecurityException extends BaseException {

    public SecurityException(HttpStatus status) {
        super(status);
    }

    public SecurityException(HttpStatus status, Object data) {
        super(status, data);
    }

    public SecurityException(Integer code, String message) {
        super(code, message);
    }

    public SecurityException(Integer code, String message, Object data) {
        super(code, message, data);
    }

}
