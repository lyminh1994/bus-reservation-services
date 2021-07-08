package vn.com.minhlq.service;

import java.util.Optional;

import vn.com.minhlq.dto.UserData;

public interface UserQueryService {

    Optional<UserData> findById(Long id);

}
