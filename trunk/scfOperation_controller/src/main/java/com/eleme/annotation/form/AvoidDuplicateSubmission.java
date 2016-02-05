package com.eleme.annotation.form;

import static java.lang.annotation.ElementType.METHOD;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 自定义防止表单重复提交的注解。新建表单页面，设置needSaveToken=true； 保存方法，设置needRemoveToken=true。
 * 
 * @author penglau
 *
 */
@Target(METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface AvoidDuplicateSubmission {

  boolean needSaveToken() default false;

  boolean needRemoveToken() default false;

}
