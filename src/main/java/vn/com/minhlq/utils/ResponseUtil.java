package vn.com.minhlq.utils;

import lombok.experimental.UtilityClass;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import vn.com.minhlq.exception.BaseException;
import vn.com.minhlq.utils.core.JSONUtil;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author Minh Ly Quang
 */
@Slf4j
@UtilityClass
public class ResponseUtil {

    /**
     * Write json to response
     *
     * @param response Response
     * @param status   Status
     * @param data     Return data
     */
    public void renderJson(HttpServletResponse response, HttpStatus status, Object data) {
        try {
            response.setHeader("Access-Control-Allow-Origin", "*");
            response.setHeader("Access-Control-Allow-Methods", "*");
            response.setContentType("application/json;charset=UTF-8");
            response.setStatus(200);
            response.getWriter().write(JSONUtil.toJsonStr(ResponseEntity.status(status).body(data)));
        } catch (IOException e) {
            log.error("Response writes JSON exception，", e);
        }
    }

    /**
     * Write json to response
     *
     * @param response  Response
     * @param exception Abnormal
     */
    public void renderJson(HttpServletResponse response, BaseException exception) {
        try {
            response.setHeader("Access-Control-Allow-Origin", "*");
            response.setHeader("Access-Control-Allow-Methods", "*");
            response.setContentType("application/json;charset=UTF-8");
            response.setStatus(200);
            response.getWriter().write(JSONUtil.toJsonStr(ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(exception)));
        } catch (IOException e) {
            log.error("Response writes JSON exception，", e);
        }
    }

}
