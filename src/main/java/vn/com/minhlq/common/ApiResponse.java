package vn.com.minhlq.common;

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
public final class ApiResponse implements Serializable {

    private static final long serialVersionUID = 8993485788201922830L;

    private Integer code;

    private String message;

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
        return ofCode(ResultCode.SUCCESS, data);
    }

    /**
     * Construct a successful and custom message API return
     *
     * @param message Return content
     * @return ApiResponse
     */
    public static ApiResponse ofMessage(String message) {
        return of(ResultCode.SUCCESS.getCode(), message, null);
    }

    /**
     * Construct a stateful API return
     *
     * @param resultCode Status {@link ResultCode}
     * @return ApiResponse
     */
    public static ApiResponse ofCode(ResultCode resultCode) {
        return ofCode(resultCode, null);
    }

    /**
     * Construct a stateful and data API return
     *
     * @param status Status {@link IResultCode}
     * @param data   Return data
     * @return ApiResponse
     */
    public static ApiResponse ofCode(IResultCode status, Object data) {
        return of(status.getCode(), status.getMessage(), data);
    }

    /**
     * Construct an abnormal API return
     *
     * @param t   abnormal
     * @param <T> {@link BaseException}  Subclass
     * @return ApiResponse
     */
    public static <T extends BaseException> ApiResponse ofException(T t) {
        return of(t.getCode(), t.getMessage(), t.getData());
    }
}
