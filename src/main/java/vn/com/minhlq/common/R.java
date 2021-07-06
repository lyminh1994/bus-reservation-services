package vn.com.minhlq.common;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @author minhlq
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public final class R<T> implements Serializable {
    private static final long serialVersionUID = 8993485788201922830L;

    private Integer code;

    private String message;

    private T data;

    public R(IResultCode resultCode) {
        this.code = resultCode.getCode();
        this.message = resultCode.getMessage();
        this.data = null;
    }

    public R(IResultCode resultCode, T data) {
        this.code = resultCode.getCode();
        this.message = resultCode.getMessage();
        this.data = data;
    }

    /**
     * Construct a custom API return
     *
     * @param code    status code
     * @param message Return content
     * @param data    Return results
     * @return ApiResponse
     */
    public static <T> R<T> of(Integer code, String message, T data) {
        return new R<T>(code, message, data);
    }

    /**
     * Construct a successful API return without data
     *
     * @return ApiResponse
     */
    public static <T> R<T> ofSuccess() {
        return ofSuccess(null);
    }

    /**
     * Construct a successful API return with data
     *
     * @param data Return data
     * @return ApiResponse
     */
    public static <T> R<T> ofSuccess(T data) {
        return ofCode(ResultCode.SUCCESS, data);
    }

    /**
     * Construct a successful and custom message API return
     *
     * @param message Return content
     * @return ApiResponse
     */
    public static <T> R<T> ofMessage(String message) {
        return of(ResultCode.SUCCESS.getCode(), message, null);
    }

    /**
     * Construct a stateful API return
     *
     * @param resultCode Status {@link ResultCode}
     * @return ApiResponse
     */
    public static <T> R<T> ofCode(ResultCode resultCode) {
        return ofCode(resultCode, null);
    }

    /**
     * Construct a stateful and data API return
     *
     * @param status Status {@link IResultCode}
     * @param data   Return data
     * @return ApiResponse
     */
    public static <T> R<T> ofCode(IResultCode status, T data) {
        return of(status.getCode(), status.getMessage(), data);
    }

    /**
     * Construct an abnormal API return
     *
     * @param t   abnormal
     * @param <T> {@link BaseException} Subclass
     * @return ApiResponse
     */
    public static <T extends BaseException> R<?> ofException(T t) {
        return of(t.getCode(), t.getMessage(), t.getData());
    }
}
