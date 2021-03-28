package vn.com.minhlq.controllers;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import vn.com.minhlq.commons.ApiResponse;
import vn.com.minhlq.commons.PageResult;
import vn.com.minhlq.commons.Status;
import vn.com.minhlq.dto.OnlineUserDto;
import vn.com.minhlq.exception.SecurityException;
import vn.com.minhlq.payload.PageCondition;
import vn.com.minhlq.security.SecurityUtils;
import vn.com.minhlq.services.MonitorService;
import vn.com.minhlq.utils.PageUtil;

import java.util.List;

/**
 * @author MinhLQ
 */
@Slf4j
@RestController
@AllArgsConstructor
public class MonitorController {

    private final MonitorService monitorService;

    @GetMapping("/monitor/online/user")
    public ApiResponse onlineUser(PageCondition pageCondition) {
        PageUtil.checkPageCondition(pageCondition, PageCondition.class);
        PageResult<OnlineUserDto> pageResult = monitorService.onlineUser(pageCondition);

        return ApiResponse.ofSuccess(pageResult);
    }

    @DeleteMapping("/monitor/online/user/kick-out")
    public ApiResponse kickOutOnlineUser(@RequestBody List<String> names) {
        if (CollectionUtils.isEmpty(names)) {
            throw new SecurityException(Status.PARAM_NOT_NULL);
        }
        if (names.contains(SecurityUtils.getCurrentUsername())) {
            throw new SecurityException(Status.KICK_OUT_SELF);
        }

        monitorService.kickOut(names);
        return ApiResponse.ofSuccess();
    }

}
