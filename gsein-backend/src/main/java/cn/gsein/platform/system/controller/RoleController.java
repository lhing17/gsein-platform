package cn.gsein.platform.system.controller;

import cn.gsein.platform.system.entity.Result;
import cn.gsein.platform.system.entity.Role;
import cn.gsein.platform.system.service.RoleService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * 角色管理
 */
@Api(value = "/api/v1/role", tags = {"角色管理"})
@RestController
@RequestMapping("/api/v1/role")
@Slf4j
public class RoleController {


    @Resource
    private RoleService roleService;

    /**
     * 根据ID获取角色
     */
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "path", dataType = "long", name = "id", value = "角色ID", required = true)
    })
    @ApiOperation(value = "根据ID获取角色", notes = "根据ID获取角色", httpMethod = "GET")
    @GetMapping("/{id}")
    Result<Role> getById(@PathVariable Long id) {
        return Result.ok(roleService.findById(id));
    }

    @GetMapping("/list")
    @ApiOperation(value = "获取角色列表", notes = "获取角色列表", httpMethod = "GET")
    Result<Page<Role>> list (@RequestParam(defaultValue = "1") Integer page, @RequestParam(defaultValue = "10") Integer size, String sort, Role role) {
        return Result.ok(roleService.findAll(page, size, sort, role));
    }

    /**
     * 保存角色
     */
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "body", dataType = "Role", name = "role", value = "角色实体", required = true)
    })
    @ApiOperation(value = "保存角色", notes = "保存角色", httpMethod = "POST")
    @PostMapping("/save")
    Result<Void> save(@RequestBody Role role) {
        roleService.save(role);
        return Result.ok();
    }

    /**
     * 修改角色
     */
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "body", dataType = "Role", name = "role", value = "角色实体", required = true)
    })
    @ApiOperation(value = "修改角色", notes = "修改角色", httpMethod = "PUT")
    @PutMapping("/update")
    Result<Void> update(@RequestBody Role role) {
        roleService.updateById(role);
        return Result.ok();
    }

    /**
     * 删除角色
     */
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "path", dataType = "long", name = "id", value = "角色ID", required = true)
    })
    @ApiOperation(value = "删除角色", notes = "删除角色", httpMethod = "DELETE")
    @DeleteMapping("/{id}")
    Result<Void> deleteById(@PathVariable Long id) {
        roleService.deleteById(id);
        return Result.ok();
    }


}
