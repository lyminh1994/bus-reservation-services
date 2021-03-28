package vn.com.minhlq.services;

import vn.com.minhlq.models.User;

import java.util.List;
import java.util.Optional;

public interface UserService {

    Optional<User> findById(Long id);

    List<User> findAll();

    User save(User user);

    void delete(Long id);

}
