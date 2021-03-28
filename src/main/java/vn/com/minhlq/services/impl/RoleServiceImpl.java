package vn.com.minhlq.services.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import vn.com.minhlq.models.Role;
import vn.com.minhlq.repositories.RolePermissionRepository;
import vn.com.minhlq.repositories.RoleRepository;
import vn.com.minhlq.services.RoleService;

import java.util.List;
import java.util.Optional;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class RoleServiceImpl implements RoleService {

    private final RoleRepository roleDao;

    private final RolePermissionRepository rolePermissionDao;

    @Override
    public Optional<Role> findById(Long id) {
        return roleDao.findById(id);
    }

    @Override
    public List<Role> findAll() {
        return roleDao.findAll();
    }

    @Override
    public Role save(Role role) {
        return roleDao.save(role);
    }

    @Override
    public void delete(Long id) {
        roleDao.deleteById(id);
    }

}
