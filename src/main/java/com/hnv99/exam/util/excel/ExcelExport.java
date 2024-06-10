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
public @interface ExcelExport {

    /** Field name */
    String value();

    /** Export order: the smaller the number, the earlier it is exported (default is to export in the order of Java class fields) */
    int sort() default 0;

    /** Export mapping, format: 0-Unknown;1-Male;2-Female */
    String kv() default "";

    /** Export template example value (if there is a value, take this value directly, no mapping) */
    String example() default "";

}
