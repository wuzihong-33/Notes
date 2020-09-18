package zihong.wu.cloudnote.common.mybatis.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface TableAttribute {
    /**
     * 表名
     * @return
     */
    String name();

    /**
     * 描述
     * @return
     */
    String comment() default "";

}
