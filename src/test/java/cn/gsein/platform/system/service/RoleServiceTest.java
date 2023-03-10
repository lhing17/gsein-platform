package cn.gsein.platform.system.service;

import cn.gsein.platform.system.entity.Role;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import javax.annotation.Resource;

@SpringBootTest
@ExtendWith(SpringExtension.class)
class RoleServiceTest {

    @Resource
    private RoleService roleService;

    @Test
    void save() {
        Role role = new Role();
        role.setName("管理员");
        role.setRoleKey("ROLE_ADMIN");
        role.setSort(1);
        role.setIsDeleted(0);
        roleService.save(role);
    }

    @Test
    void findById() {
        Role role = roleService.findById(1L);
        System.out.println(role);
    }
}
