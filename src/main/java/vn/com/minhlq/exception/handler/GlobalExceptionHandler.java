package vn.com.minhlq.exception.handler;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.servlet.NoHandlerFoundException;
import vn.com.minhlq.commons.ApiResponse;
import vn.com.minhlq.commons.BaseException;
import vn.com.minhlq.commons.Status;
import vn.com.minhlq.utils.core.CollectionUtil;
import vn.com.minhlq.utils.core.JSONUtil;

import javax.validation.ConstraintViolationException;

/**
 * @author MinhLQ
 */
@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ResponseBody
    @ExceptionHandler(value = Exception.class)
    public ApiResponse handlerException(Exception e) {
        if (e instanceof NoHandlerFoundException) {
            log.error("[Global Exception] NoHandlerFoundException: Request method {}, Request path {}", ((NoHandlerFoundException) e).getRequestURL(), ((NoHandlerFoundException) e).getHttpMethod());
            return ApiResponse.ofStatus(Status.REQUEST_NOT_FOUND);
        } else if (e instanceof HttpRequestMethodNotSupportedException) {
            log.error("[Global Exception] HttpRequestMethodNotSupportedException: Current request method {}, Support request method {}", ((HttpRequestMethodNotSupportedException) e).getMethod(), JSONUtil.toJsonStr(((HttpRequestMethodNotSupportedException) e).getSupportedHttpMethods()));
            return ApiResponse.ofStatus(Status.HTTP_BAD_METHOD);
        } else if (e instanceof MethodArgumentNotValidException) {
            log.error("[Global Exception] MethodArgumentNotValidException", e);
            return ApiResponse.of(Status.BAD_REQUEST.getStatus(), ((MethodArgumentNotValidException) e).getBindingResult().getAllErrors().get(0).getDefaultMessage(), null);
        } else if (e instanceof ConstraintViolationException) {
            log.error("[Global Exception] ConstraintViolationException", e);
            return ApiResponse.of(Status.BAD_REQUEST.getStatus(), CollectionUtil.getFirst(((ConstraintViolationException) e).getConstraintViolations()).getMessage(), null);
        } else if (e instanceof MethodArgumentTypeMismatchException) {
            log.error("[Global Exception] MethodArgumentTypeMismatchException: Parameter name {}, Error message {}", ((MethodArgumentTypeMismatchException) e).getName(), ((MethodArgumentTypeMismatchException) e).getMessage());
            return ApiResponse.ofStatus(Status.PARAM_NOT_MATCH);
        } else if (e instanceof HttpMessageNotReadableException) {
            log.error("[Global Exception] HttpMessageNotReadableException: Error message {}", ((HttpMessageNotReadableException) e).getMessage());
            return ApiResponse.ofStatus(Status.PARAM_NOT_NULL);
        } else if (e instanceof BadCredentialsException) {
            log.error("[Global Exception] BadCredentialsException: Error message {}", e.getMessage());
            return ApiResponse.ofStatus(Status.USERNAME_PASSWORD_ERROR);
        } else if (e instanceof DisabledException) {
            log.error("[Global Exception] BadCredentialsException: Error message {}", e.getMessage());
            return ApiResponse.ofStatus(Status.USER_DISABLED);
        } else if (e instanceof BaseException) {
            log.error("[Global Exception] DataManagerException: Status code {}, Exception information {}", ((BaseException) e).getStatus(), e.getMessage());
            return ApiResponse.ofException((BaseException) e);
        }

        log.error("[Global Exception]: Exception information {} ", e.getMessage());
        return ApiResponse.ofStatus(Status.ERROR);
    }

}
