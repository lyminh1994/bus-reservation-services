package vn.com.minhlq.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import vn.com.minhlq.model.Role;

import java.util.List;

/**
 * A simple Spring Data {@link JpaRepository} and {@link JpaSpecificationExecutor} for storing {@link Role}s.
 *
 * @author MinhLQ
 */
public interface RoleRepository extends JpaRepository<Role, Long>, JpaSpecificationExecutor<Role> {

    @Query(value = "SELECT role.* FROM role, user, user_role WHERE user.id = user_role.user_id AND role.id = user_role.role_id AND user.id = :userId", nativeQuery = true)
    List<Role> selectByUserId(@Param("userId") Long userId);

}
