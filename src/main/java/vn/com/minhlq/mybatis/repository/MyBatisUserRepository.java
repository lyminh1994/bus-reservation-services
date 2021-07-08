package vn.com.minhlq.mybatis.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Repository;

import lombok.AllArgsConstructor;
import vn.com.minhlq.model.Follow;
import vn.com.minhlq.model.User;
import vn.com.minhlq.mybatis.mapper.UserMapper;
import vn.com.minhlq.repository.UserRepository;

@Repository
@AllArgsConstructor
public class MyBatisUserRepository implements UserRepository {

    private final UserMapper userMapper;

    @Override
    public Optional<User> findByUsernameOrEmailOrPhone(String username, String email, String phone) {
        return Optional.ofNullable(userMapper.findByUsernameOrEmailOrPhone(username, email, phone));
    }

    @Override
    public List<User> findByUsernameIn(List<String> usernames) {
        return userMapper.findByUsernameIn(usernames);
    }

    @Override
    public Optional<User> findById(Long id) {
        return Optional.ofNullable(userMapper.findById(id));
    }

    @Override
    public Optional<User> findByUsername(String username) {
        return Optional.ofNullable(userMapper.findByUsername(username));
    }

    @Override
    public Optional<User> findByEmail(String email) {
        return Optional.ofNullable(userMapper.findByEmail(email));
    }

    @Override
    public void saveRelation(Follow followRelation) {
        if (!findRelation(followRelation.getUserId(), followRelation.getTargetId()).isPresent()) {
            userMapper.saveRelation(followRelation);
        }
    }

    @Override
    public Optional<Follow> findRelation(Long userId, Long targetId) {
        return Optional.ofNullable(userMapper.findRelation(userId, targetId));
    }

    @Override
    public void removeRelation(Follow followRelation) {
        userMapper.deleteRelation(followRelation);
    }

    @Override
    public void save(User user) {
        userMapper.insert(user);
    }

    @Override
    public List<User> findAll() {
        return userMapper.finAll();
    }

    @Override
    public void deleteById(Long id) {
        userMapper.deleteById(id);
    }

}
