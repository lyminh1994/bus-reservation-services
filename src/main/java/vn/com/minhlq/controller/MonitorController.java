package vn.com.minhlq.controller;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import vn.com.minhlq.common.PageResult;
import vn.com.minhlq.exception.BaseException;
import vn.com.minhlq.exception.SecurityException;
import vn.com.minhlq.payload.PageCondition;
import vn.com.minhlq.service.MonitorService;
import vn.com.minhlq.utils.PageUtil;
import vn.com.minhlq.security.SecurityUtil;
import vn.com.minhlq.vo.OnlineUser;

import java.util.List;

/**
 * @author Minh Ly Quang
 */
@Slf4j
@RestController
@AllArgsConstructor
public class MonitorController {

    private final MonitorService monitorService;

    @GetMapping("/monitor/online/user")
    public ResponseEntity<?> onlineUser(PageCondition pageCondition) {
        PageResult<OnlineUser> pageResult;
        try {
            PageUtil.checkPageCondition(pageCondition, PageCondition.class);
            pageResult = monitorService.onlineUser(pageCondition);
        } catch (Exception ex) {
            log.error("###onlineUser###", ex);
            throw new BaseException(HttpStatus.INTERNAL_SERVER_ERROR);
        }

        return ResponseEntity.ok(pageResult);
    }

    @DeleteMapping("/monitor/online/user/kick-out")
    public ResponseEntity<?> kickOutOnlineUser(@RequestBody List<String> names) {
        if (CollectionUtils.isEmpty(names)) {
            throw new SecurityException(HttpStatus.BAD_REQUEST, "Parameter mismatch");
        }
        if (names.contains(SecurityUtil.getCurrentUsername())){
            throw new SecurityException(HttpStatus.BAD_REQUEST, "Unable to kick out yourself manually, please try to logout");
        }
        monitorService.kickOut(names);

        return ResponseEntity.ok().build();
    }

}
