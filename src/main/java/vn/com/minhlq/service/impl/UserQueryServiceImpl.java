package vn.com.minhlq.service.impl;

import java.util.Optional;

import org.springframework.stereotype.Service;

import lombok.AllArgsConstructor;
import vn.com.minhlq.dto.UserData;
import vn.com.minhlq.mybatis.service.UserReadService;
import vn.com.minhlq.service.UserQueryService;

@Service
@AllArgsConstructor
public class UserQueryServiceImpl implements UserQueryService {

    private UserReadService userReadService;

    @Override
    public Optional<UserData> findById(Long id) {
        return Optional.ofNullable(userReadService.findById(id));
    }

}
