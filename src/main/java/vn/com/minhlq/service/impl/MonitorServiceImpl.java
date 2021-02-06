package vn.com.minhlq.service.impl;

import com.google.common.collect.Lists;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import vn.com.minhlq.common.CommonConst;
import vn.com.minhlq.common.PageResult;
import vn.com.minhlq.entity.User;
import vn.com.minhlq.payload.PageCondition;
import vn.com.minhlq.repository.UserRepository;
import vn.com.minhlq.service.MonitorService;
import vn.com.minhlq.utils.RedisUtil;
import vn.com.minhlq.security.SecurityUtil;
import vn.com.minhlq.vo.OnlineUser;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @author Minh Ly Quang
 */
@Slf4j
@Service
@Transactional
@AllArgsConstructor
public class MonitorServiceImpl implements MonitorService {

    private final RedisUtil redisUtil;

    private final UserRepository userDao;

    /**
     * Paging list of online users
     *
     * @param pageCondition Paging parameters
     * @return Paging list of online users
     */
    @Override
    public PageResult<OnlineUser> onlineUser(PageCondition pageCondition) {
        PageResult<String> keys = redisUtil.findKeysForPage(CommonConst.REDIS_JWT_KEY_PREFIX + CommonConst.SYMBOL_STAR, pageCondition.getCurrentPage(), pageCondition.getPageSize());
        List<String> rows = keys.getRows();
        Long total = keys.getTotal();

        // Get the username list according to the redis key
        List<String> usernameList = rows.stream()
                .map(s -> StringUtils.substringAfter(s, CommonConst.REDIS_JWT_KEY_PREFIX))
                .collect(Collectors.toList());
        // Query list user information based on list username
        List<User> userList = userDao.findByUsernameIn(usernameList);

        // Package online user information
        List<OnlineUser> onlineUserList = Lists.newArrayList();
        userList.forEach(user -> onlineUserList.add(OnlineUser.create(user)));

        return new PageResult<>(onlineUserList, total);
    }

    /**
     * Kick out online users
     *
     * @param names Username list
     */
    @Override
    public void kickOut(List<String> names) {
        // Clear JWT information in Redis
        List<String> redisKeys = names.parallelStream()
                .map(s -> CommonConst.REDIS_JWT_KEY_PREFIX + s)
                .collect(Collectors.toList());
        redisUtil.delete(redisKeys);

        // Get current username
        String currentUsername = SecurityUtil.getCurrentUsername();
        names.parallelStream()
                .forEach(name -> {
                    // TODO: Notify that the kicked user has been kicked out by the currently login user，
                    //  Later, consider using websocket to achieve, the specific pseudo-code implementation is as follows.
                    //  String message = "You have been manually logged off by the user [" + currentUsername + "]!";
                    log.debug("User [{}] was manually logged off by user [{}]!", name, currentUsername);
                });
    }

}
