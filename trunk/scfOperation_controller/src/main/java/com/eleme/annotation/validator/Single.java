package com.eleme.annotation.validator;

import static java.lang.annotation.ElementType.ANNOTATION_TYPE;
import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.ElementType.PARAMETER;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import javax.validation.Constraint;
import javax.validation.Payload;

/**
 * 自定义注解,唯一性验证
 * 
 * @author yonglin.zhu
 *
 */
@Target({ FIELD, METHOD, PARAMETER, ANNOTATION_TYPE })
@Retention(RUNTIME)
@Constraint(validatedBy = SingleValidator.class)
@Documented
public @interface Single {

	// 默认错误消息
	String message() default "{user.name.single}";

	// 分组
	Class<?>[] groups() default {};

	// 负载
	Class<? extends Payload>[] payload() default {};

	// 唯一性字段
	String field() default "";

	// 唯一性表名
	String tableName() default "";

	// 唯一性状态字段
	String statusField() default "";

	// 唯一性状态值;如果是单一的值,输入具体的数值;如果是 多个,以","分开;
	String status() default "";

	// 数据源选择；默认是mart,另外提供opt数据源
	String dataSource() default "mart";
}
