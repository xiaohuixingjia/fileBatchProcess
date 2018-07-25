package com.umpay.proxyservice.fileBatch.tactics.fileAnalz.impl;

import java.io.FileInputStream;
import java.util.Scanner;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.umpay.proxyservice.Constant;
import com.umpay.proxyservice.constant.Retcode;
import com.umpay.proxyservice.exception.BaseException;
import com.umpay.proxyservice.fileBatch.bean.FileProcessTask;
import com.umpay.proxyservice.fileBatch.tactics.fileAnalz.FileAnalzTactics;
import com.umpay.proxyservice.fileBatch.tactics.writer.InfoWriteTatics;
import com.umpay.proxyservice.util.LogArgsUtil;

/**
 * 下一步操作的抽象策略实现
 * 
 * @author xuxiaojia
 */
public class SimpleFileAbstractTatics implements FileAnalzTactics<FileProcessTask> {
	private static final Logger log = LoggerFactory.getLogger(SimpleFileAbstractTatics.class);
	private static final String CHARSET_NAME = "utf-8";

	@Override
	public FileProcessTask analz(FileProcessTask task, InfoWriteTatics<FileProcessTask> writeTatics) throws BaseException {
		Scanner sc =null;
		boolean readEnd=false;
		try {
			log.info(LogArgsUtil.getLogArgus() + "读入文件的文件全路径：" + task.getLocalFullFileName());
			// 初始化
			sc= new Scanner(new FileInputStream(task.getLocalFullFileName()), CHARSET_NAME);
			writeTatics.init(task);
			int linuNum = 0;
			// 逐行读取解析
			while (sc.hasNextLine()) {
				String thisLine = sc.nextLine();
				//行自加并打印当前读取行日志
				linuNum++;
				if(linuNum%Constant.ONE_THOUSAND==0){
					log.info(LogArgsUtil.getLogArgus() + "读入文件的文件全路径：" + task.getLocalFullFileName()+" 已经读到 "+linuNum+" 行");
				}
				if (isOk(thisLine, linuNum)) {
					// 转换
					thisLine = lineInfoTransfer(thisLine);
					// 写入
					writeTatics.writeIn(thisLine, linuNum);
				}
			}
			readEnd=true;
		}catch (BaseException e) {
			throw e;
		}catch (Exception e) {
			throw new BaseException(Retcode.INNER_ERROR, task.getTaskId()+" 文件解析失败",e);
		}finally {
			if (sc != null) {
				try {
					sc.close();
				} catch (Exception e) {
					log.error("文件流关闭失败",e);
				}
			}
			task=writeTatics.end(readEnd);
		}
		return task;
	}

	/**
	 * 转换行
	 * @param thisLine
	 * @return
	 */
	protected String lineInfoTransfer(String thisLine) {
		return thisLine;
	}

	/**
	 * 默认全部为true
	 * 
	 * @param lineString
	 *            当前行内容
	 * @param linuNum
	 *            当前行数
	 * @return
	 */
	protected boolean isOk(String lineString, int linuNum) {
		return true;
	}

}
