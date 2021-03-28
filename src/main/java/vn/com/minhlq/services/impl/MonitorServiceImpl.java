package vn.com.minhlq.services.impl;

import com.google.common.collect.Lists;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import vn.com.minhlq.commons.Const;
import vn.com.minhlq.commons.PageResult;
import vn.com.minhlq.dto.OnlineUserDto;
import vn.com.minhlq.models.User;
import vn.com.minhlq.payload.PageCondition;
import vn.com.minhlq.repositories.UserRepository;
import vn.com.minhlq.security.SecurityUtils;
import vn.com.minhlq.services.MonitorService;
import vn.com.minhlq.utils.RedisUtil;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @author MinhLQ
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
    public PageResult<OnlineUserDto> onlineUser(PageCondition pageCondition) {
        PageResult<String> keys = redisUtil.findKeysForPage(Const.REDIS_JWT_KEY_PREFIX + Const.SYMBOL_STAR, pageCondition.getCurrentPage(), pageCondition.getPageSize());
        List<String> rows = keys.getRows();
        Long total = keys.getTotal();

        // Get the username list according to the redis key
        List<String> usernameList = rows.stream()
                .map(s -> StringUtils.substringAfter(s, Const.REDIS_JWT_KEY_PREFIX))
                .collect(Collectors.toList());
        // Query list user information based on list username
        List<User> userList = userDao.findByUsernameIn(usernameList);

        // Package online user information
        List<OnlineUserDto> onlineUserDtoList = Lists.newArrayList();
        userList.forEach(user -> onlineUserDtoList.add(OnlineUserDto.create(user)));

        return new PageResult<>(onlineUserDtoList, total);
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
                .map(s -> Const.REDIS_JWT_KEY_PREFIX + s)
                .collect(Collectors.toList());
        redisUtil.delete(redisKeys);

        // Get current username
        String currentUsername = SecurityUtils.getCurrentUsername();
        names.parallelStream()
                .forEach(name -> {
                    // TODO: Notify that the kicked user has been kicked out by the currently login user，
                    //  Later, consider using websocket to achieve, the specific pseudo-code implementation is as follows.
                    //  String message = "You have been manually logged off by the user [" + currentUsername + "]!";
                    log.debug("User [{}] was manually logged off by user [{}]!", name, currentUsername);
                });
    }

}
