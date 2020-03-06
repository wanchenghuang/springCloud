package com.chauncy.cloud.common.annotation;

import com.chauncy.cloud.common.constraint.EnumValidator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

/**
 * @Author cheng
 * @create 2020-03-05 22:44
 */
@Target({ ElementType.FIELD, ElementType.METHOD, ElementType.ANNOTATION_TYPE })
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = EnumValidator.class)
@Documented
public @interface EnumConstraint {

    String message() default "所传的值在枚举类中不存在！";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

    /**
     * 枚举的类文件
     * @return
     */
    Class<?> target();
}
