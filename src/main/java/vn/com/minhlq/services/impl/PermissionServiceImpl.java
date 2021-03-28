package vn.com.minhlq.services.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import vn.com.minhlq.models.Permission;
import vn.com.minhlq.repositories.PermissionRepository;
import vn.com.minhlq.services.PermissionService;

import java.util.List;
import java.util.Optional;

@Slf4j
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
