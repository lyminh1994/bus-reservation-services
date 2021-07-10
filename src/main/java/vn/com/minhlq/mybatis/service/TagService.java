package vn.com.minhlq.mybatis.service;

import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface TagService {

    List<String> all();

}
