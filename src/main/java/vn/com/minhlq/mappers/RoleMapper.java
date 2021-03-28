package vn.com.minhlq.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import vn.com.minhlq.dto.RoleDto;
import vn.com.minhlq.models.Role;

/**
 * @author MinhLQ
 */
@Mapper
public interface RoleMapper {

    RoleMapper MAPPER = Mappers.getMapper(RoleMapper.class);

    Role toEntity(RoleDto roleDto);

}
