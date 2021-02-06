package vn.com.minhlq.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import vn.com.minhlq.dto.PermissionDto;
import vn.com.minhlq.entity.Permission;

/**
 * @author Minh Ly Quang
 */
@Mapper
public interface PermissionMapper {

    PermissionMapper MAPPER = Mappers.getMapper(PermissionMapper.class);

    Permission toEntity(PermissionDto newPermission);

}
