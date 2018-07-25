package com.umpay.proxyservice.anno.tacticsService.impl;

import java.lang.annotation.Annotation;
import java.util.regex.Pattern;

import com.umpay.proxyservice.anno.StringCheck;
import com.umpay.proxyservice.anno.exception.AnnoException;
import com.umpay.proxyservice.anno.tacticsService.BaseAnnoCheckService;

/**
 * 验证字符串长度
 * 
 * @author xuxiaojia
 */
public class StringLengthCheckImpl implements BaseAnnoCheckService {

	@Override
	public void check(String fieldName, Object fieldValue, Annotation annotation) throws AnnoException {
		String value = (String) fieldValue;
		StringCheck stringLengthAnno = (StringCheck) annotation;
		if((!stringLengthAnno.mustHave())&&fieldValue==null){
			return;
		}
		if (fieldValue == null) {
			throw new AnnoException(stringLengthAnno.errorMsg()+"不能为空", fieldName,stringLengthAnno.errorCode());
		}
		if(!"".equals(stringLengthAnno.maths())){
	        if( !Pattern.compile(stringLengthAnno.maths()).matcher(value).find()){
	        	throw new AnnoException(stringLengthAnno.errorMsg(), fieldName+"未通过正则校验",stringLengthAnno.errorCode());
	        }
		}
		if (stringLengthAnno.minLength() > 0) {
			if (value.length() < stringLengthAnno.minLength()) {
				throw new AnnoException(stringLengthAnno.errorMsg() + "小于最小长度" + stringLengthAnno.minLength(),
						fieldName,stringLengthAnno.errorCode());
			}
		}
		if (stringLengthAnno.maxLength() > 0) {
			if (value.length() > stringLengthAnno.maxLength()) {
				throw new AnnoException(stringLengthAnno.errorMsg() + "大于最大长度" + stringLengthAnno.maxLength(),
						fieldName,stringLengthAnno.errorCode());
			}
		}
		if (stringLengthAnno.equalLength()>0) {
			if(value.length()!=stringLengthAnno.equalLength()){
				throw new AnnoException(stringLengthAnno.errorMsg() + "不等于长度" + stringLengthAnno.equalLength(),
						fieldName,stringLengthAnno.errorCode());
			}
		}
	}

}
