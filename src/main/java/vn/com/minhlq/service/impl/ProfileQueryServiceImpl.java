package vn.com.minhlq.service.impl;

import java.util.Optional;

import org.springframework.stereotype.Service;

import vn.com.minhlq.dto.ProfileData;
import vn.com.minhlq.dto.UserData;
import vn.com.minhlq.model.User;
import vn.com.minhlq.mybatis.service.UserService;
import vn.com.minhlq.mybatis.service.UserRelationshipService;
import vn.com.minhlq.service.ProfileQueryService;

@Service
public class ProfileQueryServiceImpl implements ProfileQueryService {

    private UserService userService;

    private UserRelationshipService userRelationshipService;

    @Override
    public Optional<ProfileData> findByUsername(String username, User currentUser) {
        UserData userData = userService.findByUsername(username);
        if (userData == null) {
            return Optional.empty();
        } else {
            ProfileData profileData = new ProfileData(userData.getId(), userData.getUsername(), currentUser != null
                    && userRelationshipService.isUserFollowing(currentUser.getId(), userData.getId()));
            return Optional.of(profileData);
        }
    }

}
