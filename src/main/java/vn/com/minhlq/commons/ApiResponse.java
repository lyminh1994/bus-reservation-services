package vn.com.minhlq.commons;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @author MinhLQ
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ApiResponse implements Serializable {
    private static final long serialVersionUID = 8993485788201922830L;

    /**
     * status code
     */
    private Integer status;

    /**
     * Return content
     */
    private String message;

    /**
     * Return data
     */
    private Object data;

    /**
     * Construct a custom API return
     *
     * @param code    status code
     * @param message Return content
     * @param data    Return results
     * @return ApiResponse
     */
    public static ApiResponse of(Integer code, String message, Object data) {
        return new ApiResponse(code, message, data);
    }

    /**
     * Construct a successful API return without data
     *
     * @return ApiResponse
     */
    public static ApiResponse ofSuccess() {
        return ofSuccess(null);
    }

    /**
     * Construct a successful API return with data
     *
     * @param data Return data
     * @return ApiResponse
     */
    public static ApiResponse ofSuccess(Object data) {
        return ofStatus(Status.SUCCESS, data);
    }

    /**
     * Construct a successful and custom message API return
     *
     * @param message Return content
     * @return ApiResponse
     */
    public static ApiResponse ofMessage(String message) {
        return of(Status.SUCCESS.getStatus(), message, null);
    }

    /**
     * Construct a stateful API return
     *
     * @param status Status {@link Status}
     * @return ApiResponse
     */
    public static ApiResponse ofStatus(Status status) {
        return ofStatus(status, null);
    }

    /**
     * Construct a stateful and data API return
     *
     * @param status Status {@link IStatus}
     * @param data   Return data
     * @return ApiResponse
     */
    public static ApiResponse ofStatus(IStatus status, Object data) {
        return of(status.getStatus(), status.getMessage(), data);
    }

    /**
     * Construct an abnormal API return
     *
     * @param t   abnormal
     * @param <T> {@link BaseException}  Subclass
     * @return ApiResponse
     */
    public static <T extends BaseException> ApiResponse ofException(T t) {
        return of(t.getStatus(), t.getMessage(), t.getData());
    }
}
