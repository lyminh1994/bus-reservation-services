package vn.com.minhlq.mybatis.service;

import java.util.List;
import java.util.Set;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface UserRelationshipQueryService {

    boolean isUserFollowing(@Param("userId") Long userId, @Param("anotherUserId") Long anotherUserId);

    Set<Long> followingAuthors(@Param("userId") Long userId, @Param("ids") List<Long> ids);

    List<Long> followedUsers(@Param("userId") Long userId);

}
