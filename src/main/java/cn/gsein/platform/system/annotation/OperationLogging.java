package cn.gsein.platform.system.annotation;

import cn.gsein.platform.system.enums.OperationType;
import org.springframework.core.annotation.AliasFor;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 记录操作日志
 */
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Target({java.lang.annotation.ElementType.METHOD})
public @interface OperationLogging {

    /**
     * 操作内容描述
     */
    String value() default "";

    /**
     * 操作内容描述
     */
    @AliasFor("value")
    String description() default "";

    /**
     * 操作类型
     */
    OperationType type() default OperationType.OTHER;


}
