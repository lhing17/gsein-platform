package cn.gsein.platform.system.controller;

import cn.gsein.platform.system.entity.Result;
import cn.gsein.platform.system.entity.Role;
import cn.gsein.platform.system.service.RoleService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@RestController
@RequestMapping("/api/v1/role")
@Slf4j
public class RoleController {


    @Resource
    private RoleService roleService;

    @GetMapping("/{id}")
    Result<Role> getById(@PathVariable Long id) {
        return Result.ok(roleService.findById(id));
    }

    @PostMapping("/save")
    Result<Void> save(@RequestBody Role role) {
        roleService.save(role);
        return Result.ok();
    }

    @PutMapping("/update")
    Result<Void> update(@RequestBody Role role) {
        roleService.updateById(role);
        return Result.ok();
    }

    @DeleteMapping("/{id}")
    Result<Void> deleteById(@PathVariable Long id) {
        roleService.deleteById(id);
        return Result.ok();
    }


}
