package cn.gsein.platform.system.controller;

import cn.gsein.platform.system.entity.Permission;
import cn.gsein.platform.system.entity.Result;
import cn.gsein.platform.system.service.PermissionService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;


/**
 * 权限管理
 */
@Api(value = "/api/v1/permission", tags = {"权限管理"})
@RestController
@RequestMapping("/api/v1/permission")
@Slf4j
public class PermissionController {


    @Resource
    private PermissionService permissionService;

    /**
     * 根据ID获取权限
     */
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "path", dataType = "long", name = "id", value = "权限ID", required = true)
    })
    @ApiOperation(value = "根据ID获取权限", notes = "根据ID获取权限", httpMethod = "GET")
    @GetMapping("/{id}")
    Result<Permission> getById(@PathVariable Long id) {
        return Result.ok(permissionService.findById(id));
    }

    /**
     * 保存权限
     */
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "body", dataType = "Permission", name = "permission", value = "权限实体", required = true)
    })
    @ApiOperation(value = "保存权限", notes = "保存权限", httpMethod = "POST")
    @PostMapping("/save")
    Result<Void> save(@RequestBody Permission permission) {
        permissionService.save(permission);
        return Result.ok();
    }

    /**
     * 修改权限
     */
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "body", dataType = "Permission", name = "permission", value = "权限实体", required = true)
    })
    @ApiOperation(value = "修改权限", notes = "修改权限", httpMethod = "PUT")
    @PutMapping("/update")
    Result<Void> update(@RequestBody Permission permission) {
        permissionService.updateById(permission);
        return Result.ok();
    }

    /**
     * 删除权限
     */
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "path", dataType = "long", name = "id", value = "权限ID", required = true)
    })
    @ApiOperation(value = "删除权限", notes = "删除权限", httpMethod = "DELETE")
    @DeleteMapping("/{id}")
    Result<Void> deleteById(@PathVariable Long id) {
        permissionService.deleteById(id);
        return Result.ok();
    }


}
