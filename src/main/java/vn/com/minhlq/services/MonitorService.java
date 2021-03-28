package vn.com.minhlq.services;

import vn.com.minhlq.commons.PageResult;
import vn.com.minhlq.dto.OnlineUserDto;
import vn.com.minhlq.payload.PageCondition;

import java.util.List;

/**
 * @author MinhLQ
 */
public interface MonitorService {

    PageResult<OnlineUserDto> onlineUser(PageCondition pageCondition);

    void kickOut(List<String> names);

}
