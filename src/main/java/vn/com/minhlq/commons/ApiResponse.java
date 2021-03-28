package vn.com.minhlq.commons;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @author MinhLQ
 */
@Data
@NoArgsConstructor
public class ApiResponse implements Serializable {
    private static final long serialVersionUID = 8993485788201922830L;

    /**
     * status code
     */
    private Integer code;

    /**
     * Return content
     */
    private String message;

    /**
     * Return results
     */
    private Object results;

    /**
     * Full-parameter constructor
     *
     * @param code    status code
     * @param message Return content
     * @param results Return results
     */
    private ApiResponse(Integer code, String message, Object results) {
        this.code = code;
        this.message = message;
        this.results = results;
    }

    /**
     * Construct a custom API return
     *
     * @param code    status code
     * @param message Return content
     * @param results Return results
     * @return ApiResponse
     */
    public static ApiResponse of(Integer code, String message, Object results) {
        return new ApiResponse(code, message, results);
    }

    /**
     * Construct a successful API return without results
     *
     * @return ApiResponse
     */
    public static ApiResponse ofSuccess() {
        return ofSuccess(null);
    }

    /**
     * Construct a successful API return with results
     *
     * @param results Return results
     * @return ApiResponse
     */
    public static ApiResponse ofSuccess(Object results) {
        return ofStatus(Status.SUCCESS, results);
    }

    /**
     * Construct a successful and custom message API return
     *
     * @param message Return content
     * @return ApiResponse
     */
    public static ApiResponse ofMessage(String message) {
        return of(Status.SUCCESS.getCode(), message, null);
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
     * Construct a stateful and results API return
     *
     * @param status  Status {@link IStatus}
     * @param results Return results
     * @return ApiResponse
     */
    public static ApiResponse ofStatus(IStatus status, Object results) {
        return of(status.getCode(), status.getMessage(), results);
    }

    /**
     * Construct an abnormal API return
     *
     * @param t   abnormal
     * @param <T> {@link BaseException}  Subclass
     * @return ApiResponse
     */
    public static <T extends BaseException> ApiResponse ofException(T t) {
        return of(t.getCode(), t.getMessage(), t.getResults());
    }
}
