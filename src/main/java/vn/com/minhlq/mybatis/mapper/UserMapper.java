package vn.com.minhlq.mybatis.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import vn.com.minhlq.model.Follow;
import vn.com.minhlq.model.User;

@Mapper
public interface UserMapper {

    void insert(@Param("user") User user);

    User findByUsername(@Param("username") String username);

    User findByEmail(@Param("email") String email);

    User findById(@Param("id") Long id);

    void update(@Param("user") User user);

    Follow findRelation(@Param("userId") Long userId, @Param("targetId") Long targetId);

    void saveRelation(@Param("followRelation") Follow followRelation);

    void deleteRelation(@Param("followRelation") Follow followRelation);

    List<User> finAll();

    void deleteById(@Param("id") Long id);

    User findByUsernameOrEmailOrPhone(@Param("username") String username, @Param("email") String email,
            @Param("phone") String phone);

    List<User> findByUsernameIn(@Param("usernames") List<String> usernames);

}
