package vn.com.minhlq.service;

import java.util.List;
import java.util.Optional;

import vn.com.minhlq.model.Permission;

public interface PermissionService {

    Optional<Permission> findById(Long id);

    List<Permission> findAll();

    Permission save(Permission permission);

    void delete(Long id);

}
