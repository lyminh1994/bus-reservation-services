package vn.com.minhlq.service;

import vn.com.minhlq.common.PageResult;
import vn.com.minhlq.payload.PageCondition;
import vn.com.minhlq.vo.OnlineUser;

import java.util.List;

/**
 * @author Minh Ly Quang
 */
public interface MonitorService {

    PageResult<OnlineUser> onlineUser(PageCondition pageCondition);

    void kickOut(List<String> names);

}
