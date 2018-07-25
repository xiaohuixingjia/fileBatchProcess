package com.umpay.proxyservice.fileBatch.tactics.fileAnalz;

import com.umpay.proxyservice.exception.BaseException;
import com.umpay.proxyservice.fileBatch.bean.FileInfo;
import com.umpay.proxyservice.fileBatch.bean.FileProcessTask;
import com.umpay.proxyservice.fileBatch.tactics.writer.InfoWriteTatics;

/**
 * 文件解析策略
 * 
 * @author xuxiaojia
 */
public interface FileAnalzTactics<T extends FileInfo> {
	/**
	 * * 解析请求信息中的文件内容
	 * 
	 * @param queryContent
	 *            封装者请求信息的对象
	 * @param writeTatics
	 *            文件内容写出策略
	 * @return 返回文件处理任务对象
	 * @throws Exception
	 */
	public T analz(FileProcessTask task, InfoWriteTatics<T> writeTatics) throws BaseException;
}
