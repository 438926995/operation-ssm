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
 * 验证图片大小
 * 
 * @author huwenwen
 *
 */

@Constraint(validatedBy = {ImageSizeValidator.class})
@Target({FIELD, METHOD, PARAMETER, ANNOTATION_TYPE})
@Retention(RUNTIME)
@Documented
public @interface ImageSize {

  // 默认错误消息
  String message() default "{image.size}";

  /**
   * 最大大小（单位M）
   * 
   * @return
   */
  int maxSize() default 5;

  // 分组
  Class<?>[] groups() default {};

  // 负载
  Class<? extends Payload>[] payload() default {};

  String field() default "";
  /**
   * 是否忽略空 默认不忽略
   * @return
   */
  boolean ignoreEmpty() default false;

}
