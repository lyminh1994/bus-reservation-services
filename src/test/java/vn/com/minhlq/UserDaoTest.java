package vn.com.minhlq;

import lombok.extern.slf4j.Slf4j;
import org.assertj.core.util.Lists;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import vn.com.minhlq.entity.User;
import vn.com.minhlq.repository.UserRepository;

import java.util.List;

@Slf4j
class UserDaoTest extends BoilerplateApplicationTests {

    @Autowired
    private UserRepository userDao;

    @Test
    void findByUsernameIn() {
        List<String> usernameList = Lists.newArrayList("admin", "user");
        List<User> userList = userDao.findByUsernameIn(usernameList);
        Assert.assertEquals(2, userList.size());
        log.info("【userList】= {}", userList);
    }

}
