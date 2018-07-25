package com.umpay.proxyservice.processStep;
/**
 * 流程处理步骤
* @author xuxiaojia
 */
public interface ProcessStep <T> {
	/**
	 * 执行当前流程
	 * @param t
	 * @return
	 */
	public T process(T t);
	/**
	 * 链式执行整体流程
	 * @param t
	 * @return
	 */
	public T linkedProcess(T t);
	
}
