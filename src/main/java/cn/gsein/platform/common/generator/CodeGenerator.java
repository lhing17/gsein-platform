package cn.gsein.platform.common.generator;

import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * 用于生成实体、Controller、Service、代码的类
 */
@Service
public final class CodeGenerator {

    private final JdbcTemplate jdbcTemplate;

    public CodeGenerator(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    /**
     * 下划线转驼峰
     */
    private String underlineToCamel(String str) {
        StringBuilder result = new StringBuilder();
        String[] a = str.split("_");
        for (String s : a) {
            if (result.length() == 0) {
                result.append(s.toLowerCase());
            } else {
                result.append(s.substring(0, 1).toUpperCase());
                result.append(s.substring(1).toLowerCase());
            }
        }
        return result.toString();
    }

    public void generateEntity(String entityPath, String basePackage, String tableName, String className) {
        String sql = "select column_name, column_type, column_comment from information_schema.columns " +
                "where table_name = '" + tableName + "' and table_schema = (select database())";
        List<GseinField> fields = jdbcTemplate.queryForList(sql)
                .stream()
                .map(this::buildFieldFromMap)
                .filter(field ->
                        !Set.of("id", "createdTime", "createdBy", "updatedTime", "updatedBy", "isDeleted", "remark")
                                .contains(field.getName()))
                .toList();

        // 通过freeMarker生成实体类
        Map<String, Object> params = Map.of(
                "fields", fields,
                "className", className,
                "tableName", tableName,
                "basePackage", basePackage);

        generateClass(entityPath, "entity.java.ftl", className, params);
    }

    public void generateController(String controllerPath, String basePackage, String className) {
        // 通过freeMarker生成类
        Map<String, Object> params = Map.of(
                "className", className,
                "basePackage", basePackage);

        generateClass(controllerPath, "controller.java.ftl", className + "Controller", params);
    }

    private void generateClass(String path, String templateName, String className, Map<String, Object> params) {
        Configuration configuration = new Configuration(Configuration.VERSION_2_3_32);
        configuration.setDefaultEncoding("UTF-8");
        configuration.setClassLoaderForTemplateLoading(CodeGenerator.class.getClassLoader(), "templates");
        try {
            Template template = configuration.getTemplate(templateName);

            // 如果目录不存在，则创建
            Path p = Path.of(path);
            if (!Files.exists(p)) {
                Files.createDirectories(p);
            }

            template.process(params, Files.newBufferedWriter(Path.of(path, className + ".java")));
        } catch (IOException | TemplateException e) {
            throw new RuntimeException(e);
        }
    }

    private GseinField buildFieldFromMap(Map<String, Object> map) {
        String columnName = map.get("column_name").toString();
        String columnType = map.get("column_type").toString();
        String columnComment = map.get("column_comment").toString();

        // 获取字段类型枚举
        GseinFieldType type = GseinFieldType.of(columnType).orElse(GseinFieldType.VARCHAR);

        // 将字段名转换为驼峰命名
        columnName = underlineToCamel(columnName);

        return new GseinField(columnName, type, columnComment);
    }

    /**
     * 将path转换为包名
     */
    private String pathToPackage(String path) {
        return path.replace("/", ".").replace("\\", ".");
    }

    public void generate(String basePath, String tableName, String className) {
        String entityPath = "src/main/java/" + basePath + "/entity/";
        String basePackage = pathToPackage(basePath);
        generateEntity(entityPath, basePackage, tableName, className);

        String controllerPath = "src/main/java/" + basePath + "/controller/";
        generateController(controllerPath, basePackage, className);

        String servicePath = "src/main/java/" + basePath + "/service/";
        generateService(servicePath, basePackage, className);

        String serviceImplPath = "src/main/java/" + basePath + "/service/impl/";
        generateServiceImpl(serviceImplPath, basePackage, className);

        String daoPath = "src/main/java/" + basePath + "/dao/";
        generateDao(daoPath, basePackage, className);
    }

    private void generateDao(String daoPath, String basePackage, String className) {
        // 通过freeMarker生成类
        Map<String, Object> params = Map.of(
                "className", className,
                "basePackage", basePackage);

        generateClass(daoPath, "dao.java.ftl", className + "Dao", params);
    }

    private void generateServiceImpl(String serviceImplPath, String basePackage, String className) {
        // 通过freeMarker生成类
        Map<String, Object> params = Map.of(
                "className", className,
                "basePackage", basePackage);

        generateClass(serviceImplPath, "serviceImpl.java.ftl", className + "ServiceImpl", params);
    }

    private void generateService(String servicePath, String basePackage, String className) {
        // 通过freeMarker生成类
        Map<String, Object> params = Map.of(
                "className", className,
                "basePackage", basePackage);

        generateClass(servicePath, "service.java.ftl", className + "Service", params);
    }
}
