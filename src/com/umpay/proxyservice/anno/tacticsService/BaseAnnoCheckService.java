package com.umpay.proxyservice.anno.tacticsService;

import java.lang.annotation.Annotation;

import com.umpay.proxyservice.anno.exception.AnnoException;

/**
 * 所有注解判断的接口类
 * @author xuxiaojia
 */
public interface BaseAnnoCheckService {
	/**
	 * 各个子类需要实现的判断方法
	 * @param fieldName 字段的名字
	 * @param fieldValue 字段的值
	 * @param annotation 字段上的注解
	 * @throws AnnoException 自定义的注解校验异常
	 */
	public void check(String fieldName,Object fieldValue,Annotation annotation) throws AnnoException;
}
