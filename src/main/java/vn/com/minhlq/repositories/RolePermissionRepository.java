package vn.com.minhlq.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import vn.com.minhlq.models.RolePermission;
import vn.com.minhlq.models.unionkey.RolePermissionKey;

/**
 * A simple Spring Data {@link JpaRepository} and {@link JpaSpecificationExecutor} for storing {@link RolePermission}s.
 *
 * @author MinhLQ
 */
public interface RolePermissionRepository extends JpaRepository<RolePermission, RolePermissionKey>, JpaSpecificationExecutor<RolePermission> {
}
