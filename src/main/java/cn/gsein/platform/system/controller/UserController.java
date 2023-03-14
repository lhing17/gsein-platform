package cn.gsein.platform.system.controller;


import cn.gsein.platform.system.annotation.SystemLogging;
import cn.gsein.platform.system.entity.Result;
import cn.gsein.platform.system.entity.User;
import cn.gsein.platform.system.service.UserService;
import cn.gsein.platform.system.utils.JwtUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@RestController
@RequestMapping("/api/v1/user")
@Slf4j
@Api(tags = "用户管理")
public class UserController {


    @Resource
    private UserService userService;

    @GetMapping("/{id}")
    @ApiOperation(value = "根据用户ID获取用户")
    @ApiImplicitParam(name = "id", value = "用户ID", required = true, dataType = "Long", paramType = "path")
    @PreAuthorize("hasAuthority('system:user:list')")
    @SystemLogging
    Result<User> getById(@PathVariable Long id) {
        return Result.ok(userService.findById(id));
    }

    @PostMapping("/save")
    @ApiOperation(value = "保存用户")
    @PreAuthorize("hasAuthority('system:user:add')")
    Result<Void> save(@RequestBody User user) {
        userService.save(user);
        return Result.ok();
    }

    @PutMapping("/update")
    @ApiOperation(value = "更新用户")
    @PreAuthorize("hasAuthority('system:user:edit')")
    Result<Void> update(@RequestBody User user) {
        userService.updateById(user);
        return Result.ok();
    }

    @DeleteMapping("/{id}")
    @ApiOperation(value = "根据用户ID删除用户")
    @ApiImplicitParam(name = "id", value = "用户ID", required = true, dataType = "Long", paramType = "path")
    @PreAuthorize("hasAuthority('system:user:remove')")
    Result<Void> deleteById(@PathVariable Long id) {
        userService.deleteById(id);
        return Result.ok();
    }

    /**
     * 登录
     */
    @PostMapping("/login")
    @ApiOperation(value = "登录")
    Result<String> login(@RequestBody User user) {
        userService.login(user);
        String token = JwtUtil.generateToken(user.getId(), user.getUsername());
        return Result.ok(token);
    }

}
