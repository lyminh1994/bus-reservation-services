package vn.com.minhlq.commons;

/**
 * <p>
 * REST API Error code interface
 * </p>
 *
 * @author MinhLQ
 */
public interface IStatus {

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
