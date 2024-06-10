package com.hnv99.exam.util.excel;


import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author sunnyzyq
 * @date 2021/12/17
 */
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface ExcelImport {

    /** Field name */
    String value();

    /** Import mapping, format: 0-Unknown;1-Male;2-Female */
    String kv() default "";

    /** Whether it is a required field (default is non-required) */
    boolean required() default false;

    /** Maximum length (default is 255) */
    int maxLength() default 255;

    /** Import uniqueness validation (if multiple fields, then perform combined validation) */
    boolean unique() default false;

}
