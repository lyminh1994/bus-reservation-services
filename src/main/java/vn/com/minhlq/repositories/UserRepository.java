package vn.com.minhlq.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import vn.com.minhlq.models.User;

import java.util.List;
import java.util.Optional;

/**
 * A simple Spring Data {@link JpaRepository} and {@link JpaSpecificationExecutor} for storing {@link User}s.
 *
 * @author MinhLQ
 */
public interface UserRepository extends JpaRepository<User, Long>, JpaSpecificationExecutor<User> {

    Optional<User> findByUsernameOrEmailOrPhone(String username, String email, String phone);

    List<User> findByUsernameIn(List<String> usernameList);

}
