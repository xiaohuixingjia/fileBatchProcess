package com.umpay.proxyservice.anno;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 自定义注解
 * 长度判断
 * @author xuxiaojia
 *
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface StringCheck {
	/* 长度 等于 */
   int equalLength() default -1;
   /* 最小长度 */
   int minLength() default -1;
   /* 最大长度 */
   int maxLength() default -1;
   /* 正则验证 */
   String maths()default "";
   /* 错误信息提示 */
	String errorMsg() default "字符串校验不通过";
	/* 有错误时的错误码 */
	String errorCode() default "an_0102";
	/* 是否一定要存在 */
	boolean mustHave() default true;
}
