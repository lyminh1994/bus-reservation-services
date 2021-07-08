package vn.com.minhlq.service.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;
import vn.com.minhlq.model.Permission;
import vn.com.minhlq.repository.PermissionRepository;
import vn.com.minhlq.service.PermissionService;

@Service
@Transactional
@RequiredArgsConstructor
public class PermissionServiceImpl implements PermissionService {

    private final PermissionRepository permissionDao;

    @Override
    public Optional<Permission> findById(Long id) {
        return permissionDao.findById(id);
    }

    @Override
    public List<Permission> findAll() {
        return permissionDao.findAll();
    }

    @Override
    public Permission save(Permission permission) {
        return permissionDao.save(permission);
    }

    @Override
    public void delete(Long id) {
        permissionDao.deleteById(id);
    }

}
