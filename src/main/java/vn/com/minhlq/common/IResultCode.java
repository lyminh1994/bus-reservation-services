package vn.com.minhlq.common;

/**
 * <p>
 * REST API Error code interface
 * </p>
 *
 * @author MinhLQ
 */
public interface IResultCode {

    /**
     * Status code
     *
     * @return code
     */
    Integer getCode();

    /**
     * Messages
     *
     * @return messages
     */
    String getMessage();

}
