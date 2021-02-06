package vn.com.minhlq.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import vn.com.minhlq.entity.RolePermission;
import vn.com.minhlq.entity.unionkey.RolePermissionKey;

/**
 * A simple Spring Data {@link JpaRepository} and {@link JpaSpecificationExecutor} for storing {@link RolePermission}s.
 *
 * @author Minh Ly Quang
 */
public interface RolePermissionRepository extends JpaRepository<RolePermission, RolePermissionKey>, JpaSpecificationExecutor<RolePermission> {
}
