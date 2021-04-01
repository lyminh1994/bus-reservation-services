package vn.com.minhlq.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import vn.com.minhlq.model.Permission;

import java.util.List;

/**
 * A simple Spring Data {@link JpaRepository} and {@link JpaSpecificationExecutor} for storing {@link Permission}s.
 *
 * @author MinhLQ
 */
public interface PermissionRepository extends JpaRepository<Permission, Long>, JpaSpecificationExecutor<Permission> {

    @Query(value = "SELECT DISTINCT permission.* FROM permission INNER JOIN role_permission ON permission.id = role_permission.permission_id INNER JOIN role ON role.id = role_permission.role_id WHERE role.id IN (:ids)", nativeQuery = true)
    List<Permission> selectByListRoleIds(@Param("ids") List<Long> ids);

}
