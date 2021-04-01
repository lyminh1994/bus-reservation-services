package vn.com.minhlq.service;

import vn.com.minhlq.model.Role;

import java.util.List;
import java.util.Optional;

public interface RoleService {

    Optional<Role> findById(Long id);

    List<Role> findAll();

    Role save(Role role);

    void delete(Long id);

}
