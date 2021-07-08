package vn.com.minhlq.exception;

public class InvalidAuthenticationException extends RuntimeException {

    public InvalidAuthenticationException() {
        super("invalid email or password");
    }

}
