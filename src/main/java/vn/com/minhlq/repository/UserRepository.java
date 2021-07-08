package vn.com.minhlq.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import vn.com.minhlq.model.Follow;
import vn.com.minhlq.model.User;

/**
 * A simple Spring Data {@link JpaRepository} and
 * {@link JpaSpecificationExecutor} for storing {@link User}s.
 *
 * @author minhlq
 */
public interface UserRepository {

    Optional<User> findByUsernameOrEmailOrPhone(String username, String email, String phone);

    List<User> findByUsernameIn(List<String> usernameList);

    Optional<User> findById(Long id);

    Optional<User> findByUsername(String username);

    Optional<User> findByEmail(String email);

    void saveRelation(Follow follow);

    Optional<Follow> findRelation(Long userId, Long targetId);

    void removeRelation(Follow follow);

    void save(User user);

    List<User> findAll();

    void deleteById(Long id);

}
