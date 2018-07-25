package com.umpay.proxyservice.anno.exception;

/**
 * 注解校验抛出的自定义异常
 * 
 * @author xuxiaojia
 *
 */
public class AnnoException extends Exception{
	private static final long serialVersionUID = 3122766204291902686L;
	/* 错误信息 */
	private String errorMsg;
	/* 错误的字段 */
	private String fieldName;
	/* 错误码 */
	private String errorCode;

	public AnnoException(String msg, String fieldName,String errorCode) {
		super(errorCode+" "+fieldName+" "+msg);
		this.errorMsg = msg;
		this.fieldName = fieldName;
		this.errorCode = errorCode;
	}

	public String getErrorCode() {
		return errorCode;
	}

	public void setErrorCode(String errorCode) {
		this.errorCode = errorCode;
	}

	public String getErrorMsg() {
		return errorMsg;
	}

	public void setErrorMsg(String errorMsg) {
		this.errorMsg = errorMsg;
	}

	public String getFieldName() {
		return fieldName;
	}

	public void setFieldName(String fieldName) {
		this.fieldName = fieldName;
	}

}
