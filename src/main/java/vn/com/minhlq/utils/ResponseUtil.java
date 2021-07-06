package vn.com.minhlq.utils;

import lombok.experimental.UtilityClass;
import lombok.extern.slf4j.Slf4j;
import vn.com.minhlq.common.R;
import vn.com.minhlq.common.BaseException;
import vn.com.minhlq.common.IResultCode;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author minhlq
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
    public void renderJson(HttpServletResponse response, IResultCode status, Object data) {
        try {
            response.setHeader("Access-Control-Allow-Origin", "*");
            response.setHeader("Access-Control-Allow-Methods", "*");
            response.setContentType("application/json;charset=UTF-8");
            response.setStatus(200);
            response.getWriter().write(JSONUtil.toJsonStr(R.ofCode(status, data)));
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
            response.getWriter().write(JSONUtil.toJsonStr(R.ofException(exception)));
        } catch (IOException e) {
            log.error("Response writes JSON exception，", e);
        }
    }

}
