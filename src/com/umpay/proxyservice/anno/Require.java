package com.umpay.proxyservice.anno;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 自定义注解
 * 必输项
 * @author xuxiaojia
 *
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface Require {
    /* 错误信息提示 */
	String errorMsg() default "字段不能为空";
	/* 有错误时的错误码 */
	String errorCode() default "an_0101";

}
