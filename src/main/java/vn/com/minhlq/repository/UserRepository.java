package vn.com.minhlq.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import vn.com.minhlq.entity.User;

import java.util.List;
import java.util.Optional;

/**
 * A simple Spring Data {@link JpaRepository} and {@link JpaSpecificationExecutor} for storing {@link User}s.
 *
 * @author Minh Ly Quang
 */
public interface UserRepository extends JpaRepository<User, Long>, JpaSpecificationExecutor<User> {

    Optional<User> findByUsernameOrEmailOrPhone(String username, String email, String phone);

    List<User> findByUsernameIn(List<String> usernameList);

}
