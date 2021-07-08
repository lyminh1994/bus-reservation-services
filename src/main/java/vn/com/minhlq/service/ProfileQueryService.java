package vn.com.minhlq.service;

import java.util.Optional;

import vn.com.minhlq.dto.ProfileData;
import vn.com.minhlq.model.User;

public interface ProfileQueryService {

    Optional<ProfileData> findByUsername(String username, User currentUser);

}
