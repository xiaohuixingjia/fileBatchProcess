package com.umpay.proxyservice.fileBatch.tactics.writer.impl;

import java.util.ArrayList;
import java.util.concurrent.atomic.AtomicInteger;

import com.umpay.proxyservice.fileBatch.thread.CheckDataTpsLimitTask;
import com.umpay.proxyservice.fileBatch.thread.TaskProcessThread;
import com.umpay.proxyservice.fileBatch.thread.TpsLimitTask;
import com.umpay.proxyservice.limit.LimitCheckHandler;
/**
 * 核查数据是否一致的路径
* @author xuxiaojia
 */
public class CheckDataWriter extends QueryDataWriter {


	/**
	 * 初始化 请求和写出线程
	 */
	protected void initThread() {
		AtomicInteger diffCount = new AtomicInteger();
		list = new ArrayList<TpsLimitTask>();
		LimitCheckHandler.getInstance().putTpsLimit2map(limitInfo);
		int threadNum = configPo.getQueryThreads();
		if (threadNum <= 0) {
			threadNum = (configPo.getMaxTps() / 5) + 1;
		}
		for (int n = 0; n < threadNum; n++) {
			TpsLimitTask tpsLimitTask = new CheckDataTpsLimitTask(this, configPo,diffCount);
			new Thread(tpsLimitTask).start();
			list.add(tpsLimitTask);
		}
		
		TaskProcessThread.diffCountLocal.set(diffCount);
		
		writeResuThread = new WriterThread(this);
		writeResuThread.start();
		monitorThread=new InnerMonitorThread(this);
		monitorThread.start();
	}




}
