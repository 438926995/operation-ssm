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
 * 自定义注解，用户名唯一.
 * 
 * @author penglau
 *
 */
@Target({FIELD, METHOD, PARAMETER, ANNOTATION_TYPE})
@Retention(RUNTIME)
@Constraint(validatedBy = SingleUserNameValidator.class)
@Documented
public @interface SingleUserName {

  // 默认错误消息
  String message() default "{user.name.single}";

  // 分组
  Class<?>[]groups() default {};

  // 负载
  Class<? extends Payload>[]payload() default {};

  String field() default "";

}
