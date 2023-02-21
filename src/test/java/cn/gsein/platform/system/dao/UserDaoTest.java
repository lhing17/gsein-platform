package cn.gsein.platform.system.dao;
import java.time.LocalDateTime;
import java.util.Optional;

import cn.gsein.platform.system.entity.User;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

@SpringBootTest
@ExtendWith(SpringExtension.class)
class UserDaoTest {

    @Resource
    private UserDao userDao;

    @Test
    void save() {
        User user = new User();
        user.setUsername("张三");
        user.setGender(0);
        user.setPassword("123456");
        user.setIdCard("110110200001010001");
        user.setEmail("0000@163.com");
        user.setPhone("13333333333");
        user.setBirthday(LocalDateTime.now());
        user.setLastLogin(LocalDateTime.now());
        User user0 = userDao.save(user);
        System.out.println(user0);
    }

    @Test
    void getById() {
        Optional<User> user = userDao.findById(2);
        user.ifPresent(System.out::println);

    }

    @Test
    void updateById() {
        Optional<User> user = userDao.findById(1);
        user.ifPresent(u -> {
            u.setUsername("李四");
            User user0 = userDao.save(u);
            System.out.println(user0);
        });
    }

    @Test
    void deleteById() {
        userDao.deleteById(1);
        Assertions.assertTrue(userDao.findById(1).isEmpty());
    }

    @Test
//    @Transactional
    void logicDeleteById() {
        userDao.logicDeleteById(2);
        Assertions.assertTrue(userDao.findById(2).isEmpty());
    }
}
