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
    Integer getStatus();

    /**
     * Messages
     *
     * @return messages
     */
    String getMessage();

}
