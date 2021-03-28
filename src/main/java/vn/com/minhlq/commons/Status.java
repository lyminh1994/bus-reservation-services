package vn.com.minhlq.commons;

import lombok.Getter;

/**
 * <p>
 * General status code
 * </p>
 *
 * @author MinhLQ
 */
@Getter
public enum Status implements IStatus {
    SUCCESS(200, "OK"),

    ERROR(500, "Internal Server Error"),

    LOGOUT(200, "Logout OK"),

    UNAUTHORIZED(401, "Unauthorized"),

    ACCESS_DENIED(403, "Forbidden"),

    REQUEST_NOT_FOUND(404, "Not Found"),

    HTTP_BAD_METHOD(405, "Method Not Allowed"),

    BAD_REQUEST(400, "Bad Request"),

    PARAM_NOT_MATCH(400, "Parameter mismatch"),

    PARAM_NOT_NULL(400, "Parameter cannot be empty"),

    USER_DISABLED(403, "The current user has been locked, please contact the administrator to unlock"),

    USERNAME_PASSWORD_ERROR(5001, "Invalid username or password"),

    TOKEN_EXPIRED(5002, "Token has expired, please try to login again"),

    TOKEN_PARSE_ERROR(5002, "Token parsing failed, please try to login again"),

    TOKEN_OUT_OF_CTRL(5003, "Your account has login somewhere, please change password or try to login again"),

    KICK_OUT_SELF(5004, "Unable to kick out yourself manually, please try to logout");

    private Integer status;

    private String message;

    Status(Integer status, String message) {
        this.status = status;
        this.message = message;
    }

    public static Status fromCode(Integer code) {
        Status[] statuses = Status.values();
        for (Status status : statuses) {
            if (status.getStatus().equals(code)) {
                return status;
            }
        }
        return SUCCESS;
    }

    @Override
    public String toString() {
        return String.format(" Status:{status=%s, message=%s} ", this.getStatus(), getMessage());
    }

}