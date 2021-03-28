package vn.com.minhlq.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import vn.com.minhlq.models.UserRole;
import vn.com.minhlq.models.unionkey.UserRoleKey;

/**
 * A simple Spring Data {@link JpaRepository} and {@link JpaSpecificationExecutor} for storing {@link UserRole}s.
 *
 * @author MinhLQ
 */
public interface UserRoleRepository extends JpaRepository<UserRole, UserRoleKey>, JpaSpecificationExecutor<UserRole> {
}
