package vn.com.minhlq.enumdef;

import lombok.Getter;

/**
 * @author Minh Ly Quang
 */
@Getter
public enum StatusEnum implements IStatusEnum {

    SUCCESS(200, "OK"),
    ERROR(500, "Internal Server Error"),
    LOGOUT(200, "Logout successfully!"),
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

    private final Integer code;

    private final String message;

    StatusEnum(Integer code, String message) {
        this.code = code;
        this.message = message;
    }

    public static StatusEnum fromCode(Integer code) {
        StatusEnum[] statuses = StatusEnum.values();
        for (StatusEnum status : statuses) {
            if (status.getCode().equals(code)) {
                return status;
            }
        }
        return SUCCESS;
    }

    @Override
    public String toString() {
        return String.format(" Status:{code=%s, message=%s} ", getCode(), getMessage());
    }

}
