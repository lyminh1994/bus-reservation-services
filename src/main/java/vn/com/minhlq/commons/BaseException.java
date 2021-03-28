package vn.com.minhlq.commons;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * @author MinhLQ
 */
@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class BaseException extends RuntimeException {

    private Integer status;
    private String message;
    private Object data;

    public BaseException(Status status) {
        super(status.getMessage());
        this.status = status.getStatus();
        this.message = status.getMessage();
    }

    public BaseException(Status status, Object data) {
        this(status);
        this.data = data;
    }

    public BaseException(Integer status, String message) {
        super(message);
        this.status = status;
        this.message = message;
    }

    public BaseException(Integer status, String message, Object data) {
        this(status, message);
        this.data = data;
    }

}
