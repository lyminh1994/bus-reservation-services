package vn.com.minhlq;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import vn.com.minhlq.entity.*;
import vn.com.minhlq.entity.unionkey.RolePermissionKey;
import vn.com.minhlq.entity.unionkey.UserRoleKey;
import vn.com.minhlq.repository.*;
import vn.com.minhlq.utils.core.DateUtil;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

class DataInitTest extends BoilerplateApplicationTests {

    @Autowired
    private UserRepository userDao;

    @Autowired
    private RoleRepository roleDao;

    @Autowired
    private PermissionRepository permissionDao;

    @Autowired
    private UserRoleRepository userRoleDao;

    @Autowired
    private RolePermissionRepository rolePermissionDao;

    @Autowired
    private BCryptPasswordEncoder encoder;

    @Test
    void initTest() throws ParseException {
        init();
    }

    private void init() throws ParseException {
        User admin = createUser(true);
        User user = createUser(false);

        Role roleAdmin = createRole(true);
        Role roleUser = createRole(false);

        createUserRoleRelation(admin.getId(), roleAdmin.getId());
        createUserRoleRelation(user.getId(), roleUser.getId());

        // Page permissions
        Permission testPagePerm = createPermission("/test", "Test page", 1, "page:test", null, 1, 0L);
        // Button permissions
        Permission testBtnQueryPerm = createPermission("/**/test", "Test page-query", 2, "btn:test:query", "GET", 1, testPagePerm.getId());
        Permission testBtnPermInsert = createPermission("/**/test", "Test page-add", 2, "btn:test:insert", "POST", 2, testPagePerm.getId());

        Permission monitorOnlinePagePerm = createPermission("/monitor", "Monitor online user page", 1, "page:monitor:online", null, 2, 0L);
        Permission monitorOnlineBtnQueryPerm = createPermission("/**/api/monitor/online/user", "Online user page-query", 2, "btn:monitor:online:query", "GET", 1, monitorOnlinePagePerm.getId());
        Permission monitorOnlineBtnKickOutPerm = createPermission("/**/api/monitor/online/user/kick-out", "Online user page-kickOut", 2, "btn:monitor:online:kickout", "DELETE", 2, monitorOnlinePagePerm.getId());

        createRolePermissionRelation(roleAdmin.getId(), testPagePerm.getId());
        createRolePermissionRelation(roleUser.getId(), testPagePerm.getId());
        createRolePermissionRelation(roleAdmin.getId(), testBtnQueryPerm.getId());
        createRolePermissionRelation(roleUser.getId(), testBtnQueryPerm.getId());
        createRolePermissionRelation(roleAdmin.getId(), testBtnPermInsert.getId());
        createRolePermissionRelation(roleAdmin.getId(), monitorOnlinePagePerm.getId());
        createRolePermissionRelation(roleAdmin.getId(), monitorOnlineBtnQueryPerm.getId());
        createRolePermissionRelation(roleAdmin.getId(), monitorOnlineBtnKickOutPerm.getId());
    }

    private void createRolePermissionRelation(Long roleId, Long permissionId) {
        RolePermission adminPage = new RolePermission();
        RolePermissionKey adminPageKey = new RolePermissionKey();
        adminPageKey.setRoleId(roleId);
        adminPageKey.setPermissionId(permissionId);
        adminPage.setId(adminPageKey);
        rolePermissionDao.save(adminPage);
    }

    private Permission createPermission(String url, String name, Integer type, String permission, String method, Integer sort, Long parentId) {
        Permission perm = new Permission();
        perm.setUrl(url);
        perm.setName(name);
        perm.setType(type);
        perm.setExpression(permission);
        perm.setMethod(method);
        perm.setSort(sort);
        perm.setParentId(parentId);
        permissionDao.save(perm);
        return perm;
    }

    private void createUserRoleRelation(Long userId, Long roleId) {
        UserRole userRole = new UserRole();
        UserRoleKey key = new UserRoleKey();
        key.setUserId(userId);
        key.setRoleId(roleId);
        userRole.setId(key);
        userRoleDao.save(userRole);
    }

    private Role createRole(boolean isAdmin) {
        Role role = new Role();
        role.setName(isAdmin ? "Administrator" : "General user");
        role.setDescription(isAdmin ? "Super administrator" : "General user");
        role.setCreateTime(DateUtil.current(false));
        role.setUpdateTime(DateUtil.current(false));
        roleDao.save(role);
        return role;
    }

    private User createUser(boolean isAdmin) throws ParseException {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date birthday = simpleDateFormat.parse("1994-11-22");
        User user = new User();
        user.setUsername(isAdmin ? "admin" : "user");
        user.setNickname(isAdmin ? "Administrator" : "General user");
        user.setPassword(encoder.encode("123456"));
        user.setBirthday(birthday.getTime());
        user.setEmail((isAdmin ? "admin" : "user") + "@gmail.com");
        user.setPhone(isAdmin ? "17300000000" : "17300001111");
        user.setGender(1);
        user.setStatus(1);
        user.setCreateTime(DateUtil.current(false));
        user.setUpdateTime(DateUtil.current(false));
        userDao.save(user);
        return user;
    }

}
