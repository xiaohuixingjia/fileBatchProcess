package com.umpay.proxyservice.anno.tacticsService.impl;

import java.lang.annotation.Annotation;

import com.umpay.proxyservice.anno.Require;
import com.umpay.proxyservice.anno.exception.AnnoException;
import com.umpay.proxyservice.anno.tacticsService.BaseAnnoCheckService;

/**
 * 验证必输
 * @author xuxiaojia
 */
public class RequireAnnoCheckImpl implements BaseAnnoCheckService {

	@Override
	public void check(String fieldName,Object fieldValue,Annotation annotation) throws AnnoException {
		if(fieldValue==null||(fieldValue+"").equals("")){
			throw new AnnoException(((Require)annotation).errorMsg(), fieldName,((Require)annotation).errorCode());
		}
	}

}
