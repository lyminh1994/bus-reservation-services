package vn.com.minhlq.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import vn.com.minhlq.model.UserRole;
import vn.com.minhlq.model.unionkey.UserRoleKey;

/**
 * A simple Spring Data {@link JpaRepository} and
 * {@link JpaSpecificationExecutor} for storing {@link UserRole}s.
 *
 * @author minhlq
 */
public interface UserRoleRepository extends JpaRepository<UserRole, UserRoleKey>, JpaSpecificationExecutor<UserRole> {
}
