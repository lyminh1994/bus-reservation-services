package vn.com.minhlq.service;

import vn.com.minhlq.entity.Permission;

import java.util.List;
import java.util.Optional;

public interface PermissionService {

    Optional<Permission> findById(Long id);

    List<Permission> findAll();

    Permission save(Permission permission);

    void delete(Long id);

}
