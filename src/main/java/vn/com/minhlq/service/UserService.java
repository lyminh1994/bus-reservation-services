package vn.com.minhlq.service;

import vn.com.minhlq.model.User;

import java.util.List;
import java.util.Optional;

public interface UserService {

    Optional<User> findById(Long id);

    List<User> findAll();

    User save(User user);

    void delete(Long id);

}
