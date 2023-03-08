package cn.gsein.platform.system.controller;

import cn.gsein.platform.system.entity.Result;
import cn.gsein.platform.system.entity.Permission;
import cn.gsein.platform.system.service.PermissionService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@RestController
@RequestMapping("/api/v1/permission")
@Slf4j
public class PermissionController {


    @Resource
    private PermissionService permissionService;

    @GetMapping("/{id}")
    Result<Permission> getById(@PathVariable Long id) {
        return Result.ok(permissionService.findById(id));
    }

    @PostMapping("/save")
    Result<Void> save(@RequestBody Permission permission) {
        permissionService.save(permission);
        return Result.ok();
    }

    @PutMapping("/update")
    Result<Void> update(@RequestBody Permission permission) {
        permissionService.updateById(permission);
        return Result.ok();
    }

    @DeleteMapping("/{id}")
    Result<Void> deleteById(@PathVariable Long id) {
        permissionService.deleteById(id);
        return Result.ok();
    }


}
