package cn.gsein.platform.system.service;

import cn.gsein.platform.system.entity.User;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import javax.annotation.Resource;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@ExtendWith(SpringExtension.class)
class UserServiceTest {

    @Resource
    private UserService userService;

    @Test
    void getById() {
        User user = userService.findById(2L);
        System.out.println(user);
    }
}
