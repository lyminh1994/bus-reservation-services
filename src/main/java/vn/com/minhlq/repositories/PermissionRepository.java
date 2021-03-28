package vn.com.minhlq.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import vn.com.minhlq.models.Permission;

import java.util.List;

/**
 * A simple Spring Data {@link JpaRepository} and {@link JpaSpecificationExecutor} for storing {@link Permission}s.
 *
 * @author MinhLQ
 */
public interface PermissionRepository extends JpaRepository<Permission, Long>, JpaSpecificationExecutor<Permission> {

    @Query(value = "SELECT DISTINCT permission.* FROM permission, role, role_permission WHERE role.id = role_permission.role_id AND permission.id = role_permission.permission_id AND role.id IN (:ids)", nativeQuery = true)
    List<Permission> selectByRoleIdList(@Param("ids") List<Long> ids);

}
