package vn.com.minhlq.services;

import vn.com.minhlq.models.Role;

import java.util.List;
import java.util.Optional;

public interface RoleService {

    Optional<Role> findById(Long id);

    List<Role> findAll();

    Role save(Role role);

    void delete(Long id);

}
