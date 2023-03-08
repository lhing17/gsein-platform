package ${basePackage}.controller;

import cn.gsein.platform.system.entity.Result;
import ${basePackage}.entity.${className};
import ${basePackage}.service.${className}Service;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@RestController
@RequestMapping("/api/v1/${className?uncap_first}")
@Slf4j
public class ${className}Controller {


    @Resource
    private ${className}Service ${className?uncap_first}Service;

    @GetMapping("/{id}")
    Result<${className}> getById(@PathVariable Long id) {
        return Result.ok(${className?uncap_first}Service.findById(id));
    }

    @PostMapping("/save")
    Result<Void> save(@RequestBody ${className} ${className?uncap_first}) {
        ${className?uncap_first}Service.save(${className?uncap_first});
        return Result.ok();
    }

    @PutMapping("/update")
    Result<Void> update(@RequestBody ${className} ${className?uncap_first}) {
        ${className?uncap_first}Service.updateById(${className?uncap_first});
        return Result.ok();
    }

    @DeleteMapping("/{id}")
    Result<Void> deleteById(@PathVariable Long id) {
        ${className?uncap_first}Service.deleteById(id);
        return Result.ok();
    }


}
