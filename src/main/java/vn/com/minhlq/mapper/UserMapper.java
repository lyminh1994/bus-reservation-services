package vn.com.minhlq.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import vn.com.minhlq.dto.UserDto;
import vn.com.minhlq.entity.User;

/**
 * @author Minh Ly Quang
 */
@Mapper
public interface UserMapper {

    UserMapper MAPPER = Mappers.getMapper(UserMapper.class);

    User toEntity(UserDto userDto);

}
