package cn.gsein.platform.system.controller;


import cn.gsein.platform.system.entity.Result;
import cn.gsein.platform.system.entity.User;
import cn.gsein.platform.system.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@RestController
@RequestMapping("/api/v1/user")
@Slf4j
public class UserController {

    @Resource
    private UserService userService;

    @GetMapping("/{id}")
    Result<User> getById(@PathVariable Long id) {
        log.info("查询用户信息，id：{}", id);
        return Result.ok(userService.findById(id));
    }

    @PostMapping("/save")
    Result<Void> save(@RequestBody User user) {
        userService.save(user);
        return Result.ok();
    }

    @PutMapping("/update")
    Result<Void> update(@RequestBody User user) {
        userService.updateById(user);
        return Result.ok();
    }

    @DeleteMapping("/{id}")
    Result<Void> deleteById(@PathVariable Long id) {
        userService.deleteById(id);
        return Result.ok();
    }

    /**
     * 登录
     */
    @PostMapping("/login")
    Result<Void> login(@RequestBody User user) {
        userService.login(user);
        return Result.ok();
    }

}
