package com.hnv99.exam.common.handler;

import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import com.hnv99.exam.util.DateTimeUtil;
import com.hnv99.exam.util.SecurityUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.reflection.MetaObject;
import org.springframework.stereotype.Component;

import java.lang.reflect.Field;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Objects;

/**
 * MyBatis Plus common field fill handler (meta-object handler)
 */
@Component
@Slf4j
public class FiledFullHandler implements MetaObjectHandler {
    @Override
    public void insertFill(MetaObject metaObject) {
        // Automatically fill if creator ID is not present; use attribute name rather than field name
        Class<?> clazz = metaObject.getOriginalObject().getClass();
        Field[] fields = clazz.getDeclaredFields();
        Arrays.stream(fields).forEach(field -> {
            // Fill creator ID
            if ("userId".equals(field.getName()) && (Objects.isNull(getFieldValByName("userId", metaObject)))) {
                log.info("The user_id field meets the common field auto-fill rule and has been filled");
                this.strictInsertFill(metaObject, "userId", Integer.class, SecurityUtil.getUserId());
            }
            // Fill creation time
            if ("createTime".equals(field.getName()) && (Objects.isNull(getFieldValByName("createTime", metaObject)))) {
                log.info("The create_time field meets the common field auto-fill rule and has been filled");
                this.strictInsertFill(metaObject, "createTime", LocalDateTime.class, DateTimeUtil.getDateTime());
            }
        });
    }

    @Override
    public void updateFill(MetaObject metaObject) {
        // Implement the update fill logic here if necessary
    }
}
