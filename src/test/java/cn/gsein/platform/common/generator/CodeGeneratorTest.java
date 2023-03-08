package cn.gsein.platform.common.generator;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import javax.annotation.Resource;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@ExtendWith(SpringExtension.class)
class CodeGeneratorTest {

    @Resource
    private CodeGenerator codeGenerator;

    @Test
    void generateEntity() {
        String path = "src/main/java/cn/gsein/platform/business/entity/";
        String entityPackage = "cn.gsein.platform.business.entity";
        codeGenerator.generateEntity(path, entityPackage, "system_user", "User");
    }

    @Test
    void generate() {
        String basePath = "cn/gsein/platform/system";
        codeGenerator.generate(basePath, "system_permission", "Permission");
    }
}
