package com.umpay.proxyservice.fileBatch.tactics.writer;

import com.umpay.proxyservice.exception.BaseException;
import com.umpay.proxyservice.fileBatch.bean.FileInfo;

/**
 * 写信息策略
 * 
 * @author xuxiaojia
 */
public interface InfoWriteTatics <T extends FileInfo> {
	/**
	 * 写策略初始化
	 */
	public void init(T t) throws BaseException;

	/**
	 * 写入
	 * 
	 * @param lineInfo 行信息
	 * @param lineNum 当前行数
	 */
	public void writeIn(String lineInfo,int lineNum) throws BaseException;

	/**
	 * 读取结束释放写出策略的资源
	 * @param readEnd-- 是否读取结束调用end方法，如果是异常中断则传入false
	 * @return
	 * @throws BaseException
	 */
	public T end(boolean readEnd) throws BaseException;
}
