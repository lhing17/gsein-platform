package cn.gsein.platform.system.controller;

import cn.gsein.platform.system.entity.Result;
import cn.gsein.platform.system.entity.Dept;
import cn.gsein.platform.system.service.DeptService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@RestController
@RequestMapping("/api/v1/dept")
@Slf4j
public class DeptController {


    @Resource
    private DeptService deptService;

    @GetMapping("/{id}")
    Result<Dept> getById(@PathVariable Long id) {
        return Result.ok(deptService.findById(id));
    }

    @PostMapping("/save")
    Result<Void> save(@RequestBody Dept dept) {
        deptService.save(dept);
        return Result.ok();
    }

    @PutMapping("/update")
    Result<Void> update(@RequestBody Dept dept) {
        deptService.updateById(dept);
        return Result.ok();
    }

    @DeleteMapping("/{id}")
    Result<Void> deleteById(@PathVariable Long id) {
        deptService.deleteById(id);
        return Result.ok();
    }


}
