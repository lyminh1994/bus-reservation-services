package vn.com.minhlq.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import vn.com.minhlq.model.Role;
import vn.com.minhlq.repository.RolePermissionRepository;
import vn.com.minhlq.repository.RoleRepository;
import vn.com.minhlq.service.RoleService;

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
