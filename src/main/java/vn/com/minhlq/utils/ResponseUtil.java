package vn.com.minhlq.utils;

import lombok.experimental.UtilityClass;
import lombok.extern.slf4j.Slf4j;
import vn.com.minhlq.commons.ApiResponse;
import vn.com.minhlq.commons.BaseException;
import vn.com.minhlq.commons.IStatus;
import vn.com.minhlq.utils.core.JSONUtil;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author MinhLQ
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
    public void renderJson(HttpServletResponse response, IStatus status, Object data) {
        try {
            response.setHeader("Access-Control-Allow-Origin", "*");
            response.setHeader("Access-Control-Allow-Methods", "*");
            response.setContentType("application/json;charset=UTF-8");
            response.setStatus(200);
            response.getWriter().write(JSONUtil.toJsonStr(ApiResponse.ofStatus(status, data)));
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
            response.getWriter().write(JSONUtil.toJsonStr(ApiResponse.ofException(exception)));
        } catch (IOException e) {
            log.error("Response writes JSON exception，", e);
        }
    }

}
