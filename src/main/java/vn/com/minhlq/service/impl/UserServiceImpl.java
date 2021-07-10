package vn.com.minhlq.service.impl;

import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.AllArgsConstructor;
import vn.com.minhlq.dto.RegisterParam;
import vn.com.minhlq.dto.UpdateUserCommand;
import vn.com.minhlq.dto.UpdateUserParam;
import vn.com.minhlq.model.User;
import vn.com.minhlq.repository.UserRepository;
import vn.com.minhlq.service.UserService;
import vn.com.minhlq.utils.CryptoUtils;

@Service
@Transactional
@AllArgsConstructor
public class UserServiceImpl implements UserService {

    private UserRepository userRepository;

    @Override
    public Optional<User> findById(Long id) {
        return userRepository.findById(id);
    }

    @Override
    public List<User> findAll() {
        return userRepository.findAll();
    }

    @Override
    public void save(User user) {
        userRepository.save(user);
    }

    @Override
    public void delete(Long id) {
        userRepository.deleteById(id);
    }

    @Override
    public User createUser(@Valid RegisterParam registerParam) {
        User user = new User(registerParam.getEmail(), registerParam.getUsername(),
                CryptoUtils.encrypt(registerParam.getPassword()));
        userRepository.save(user);
        return user;
    }

    @Override
    public void updateUser(@Valid UpdateUserCommand command) {
        User user = command.getTargetUser();
        UpdateUserParam updateUserParam = command.getParam();
        user.update(updateUserParam.getEmail(), updateUserParam.getUsername(), updateUserParam.getPassword());
        userRepository.save(user);
    }

}
