package vn.com.minhlq.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import vn.com.minhlq.dto.PermissionDto;
import vn.com.minhlq.models.Permission;

/**
 * @author MinhLQ
 */
@Mapper
public interface PermissionMapper {

    PermissionMapper MAPPER = Mappers.getMapper(PermissionMapper.class);

    Permission toEntity(PermissionDto newPermission);

}
