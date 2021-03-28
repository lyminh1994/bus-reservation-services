package vn.com.minhlq.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import vn.com.minhlq.dto.UserDto;
import vn.com.minhlq.models.User;

/**
 * @author MinhLQ
 */
@Mapper
public interface UserMapper {

    UserMapper MAPPER = Mappers.getMapper(UserMapper.class);

    User toEntity(UserDto userDto);

}
