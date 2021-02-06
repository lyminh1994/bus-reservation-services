package vn.com.minhlq.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import vn.com.minhlq.dto.RoleDto;
import vn.com.minhlq.entity.Role;

/**
 * @author Minh Ly Quang
 */
@Mapper
public interface RoleMapper {

    RoleMapper MAPPER = Mappers.getMapper(RoleMapper.class);

    Role toEntity(RoleDto roleDto);

}
