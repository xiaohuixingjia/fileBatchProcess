package com.umpay.proxyservice.anno;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import com.umpay.proxyservice.handler.ProcessExecuteHandler.ProcessType;

/**
 * 校验类型  校验组分离注解
 * @author xuxiaojia
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface CheckTypeAnno {
	ProcessType[] value() ;
}
