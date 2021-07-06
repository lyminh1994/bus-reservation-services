package vn.com.minhlq.common;

/**
 * <p>
 * REST API Error code interface
 * </p>
 *
 * @author minhlq
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
