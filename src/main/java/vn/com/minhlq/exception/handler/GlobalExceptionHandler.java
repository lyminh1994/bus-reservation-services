package vn.com.minhlq.exception.handler;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.servlet.NoHandlerFoundException;
import vn.com.minhlq.exception.BaseException;
import vn.com.minhlq.utils.core.CollectionUtil;
import vn.com.minhlq.utils.core.JSONUtil;

import javax.validation.ConstraintViolationException;

/**
 * @author Minh Ly Quang
 */
@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(value = Exception.class)
    public ResponseEntity<?> handlerException(Exception e) {
        if (e instanceof NoHandlerFoundException) {
            log.error("[Global Exception] NoHandlerFoundException: Request method {}, Request path {}", ((NoHandlerFoundException) e).getRequestURL(), ((NoHandlerFoundException) e).getHttpMethod());
            return ResponseEntity.notFound().build();
        } else if (e instanceof HttpRequestMethodNotSupportedException) {
            log.error("[Global Exception] HttpRequestMethodNotSupportedException: Current request method {}, Support request method {}", ((HttpRequestMethodNotSupportedException) e).getMethod(), JSONUtil.toJsonStr(((HttpRequestMethodNotSupportedException) e).getSupportedHttpMethods()));
            return ResponseEntity.status(HttpStatus.METHOD_NOT_ALLOWED).build();
        } else if (e instanceof MethodArgumentNotValidException) {
            log.error("[Global Exception] MethodArgumentNotValidException", e);
            return ResponseEntity.badRequest().body(((MethodArgumentNotValidException) e).getBindingResult().getAllErrors().get(0).getDefaultMessage());
        } else if (e instanceof ConstraintViolationException) {
            log.error("[Global Exception] ConstraintViolationException", e);
            return ResponseEntity.badRequest().body(CollectionUtil.getFirst(((ConstraintViolationException) e).getConstraintViolations()).getMessage());
        } else if (e instanceof MethodArgumentTypeMismatchException) {
            log.error("[Global Exception] MethodArgumentTypeMismatchException: Parameter name {}, Error message {}", ((MethodArgumentTypeMismatchException) e).getName(), ((MethodArgumentTypeMismatchException) e).getMessage());
            return ResponseEntity.badRequest().body("Parameter mismatch");
        } else if (e instanceof HttpMessageNotReadableException) {
            log.error("[Global Exception] HttpMessageNotReadableException: Error message {}", ((HttpMessageNotReadableException) e).getMessage());
            return ResponseEntity.badRequest().body("Parameter cannot be empty");
        } else if (e instanceof BadCredentialsException) {
            log.error("[Global Exception] BadCredentialsException: Error message {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid username or password");
        } else if (e instanceof DisabledException) {
            log.error("[Global Exception] BadCredentialsException: Error message {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("The current user has been locked, please contact the administrator to unlock");
        } else if (e instanceof BaseException) {
            log.error("[Global Exception] DataManagerException: Status code {}, Exception information {}", ((BaseException) e).getCode(), e.getMessage());
            return ResponseEntity.status(((BaseException) e).getCode()).body(e.getMessage());
        }

        log.error("[Global Exception]: Exception information {} ", e.getMessage());
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
    }

}
