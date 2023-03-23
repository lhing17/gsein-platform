package cn.gsein.platform.system.annotation;

import cn.gsein.platform.system.enums.SystemLoggingLevel;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 用在Controller的方法上，表示该方法需要记录日志，日志内容为方法名称、方法的参数和返回值
 */
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Target({java.lang.annotation.ElementType.METHOD})
public @interface SystemLogging {

    SystemLoggingLevel level() default SystemLoggingLevel.INFO;

    boolean logParams() default true;

    boolean logResult() default true;

    boolean logException() default true;
}
