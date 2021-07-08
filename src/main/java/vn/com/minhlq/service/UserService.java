package vn.com.minhlq.service;

import java.util.List;
import java.util.Optional;

import vn.com.minhlq.dto.RegisterParam;
import vn.com.minhlq.dto.UpdateUserCommand;
import vn.com.minhlq.model.User;

public interface UserService {

    Optional<User> findById(Long id);

    List<User> findAll();

    void save(User user);

    void delete(Long id);

    User createUser(RegisterParam registerParam);

    void updateUser(UpdateUserCommand command);

}
