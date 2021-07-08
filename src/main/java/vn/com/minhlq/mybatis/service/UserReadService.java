package vn.com.minhlq.mybatis.service;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import vn.com.minhlq.dto.UserData;

@Mapper
public interface UserReadService {

    UserData findByUsername(@Param("username") String username);

    UserData findById(@Param("id") Long id);

}
