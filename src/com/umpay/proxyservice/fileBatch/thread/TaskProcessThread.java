package com.umpay.proxyservice.fileBatch.thread;

import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.umpay.proxyservice.Constant;
import com.umpay.proxyservice.fileBatch.bean.FileProcessTask;
import com.umpay.proxyservice.fileBatch.service.FileProcessTaskService;
import com.umpay.proxyservice.handler.ProcessExecuteHandler;
import com.umpay.proxyservice.ruleTimeService.RuleTimeService;
import com.umpay.proxyservice.util.LogArgsUtil;
import com.umpay.proxyservice.util.WarnMsgUtil;

/**
 * 文件处理线程
 * 
 * @author xuxiaojia
 */
public class TaskProcessThread {
	private static final Logger log = LoggerFactory.getLogger(TaskProcessThread.class);
	@Autowired
	private FileProcessTaskService fileProcessTaskService;
	public static final AtomicBoolean isRun = new AtomicBoolean(true);
	
	public static final ThreadLocal<AtomicInteger> diffCountLocal = new ThreadLocal<AtomicInteger>();
		

	public void process() {
		if (!isRun.get()) {
			log.info("停止执行新的任务！！！！！");
		}
		FileProcessTask oneTask = null;
		long startTime = System.currentTimeMillis();
		try {
			oneTask = fileProcessTaskService.getOneTask(FileProcessTaskService.TaskStore.WAITING);
			if (oneTask != null&&ProcessExecuteHandler.getInstance().thisTaskCanProcess(oneTask)) {
				log.info(oneTask.getTaskId() + "任务开始执行");
				LogArgsUtil.setLogArgs(oneTask.getTaskId());
				fileProcessTaskService.putTask(oneTask, FileProcessTaskService.TaskStore.DEALING);
				ProcessExecuteHandler.getInstance().linkedExecute(oneTask);
				String content = generContent(oneTask,diffCountLocal.get());
				WarnMsgUtil.sendEmail("文件批处理任务执行结束", content);
				fileProcessTaskService.removeTask(oneTask, FileProcessTaskService.TaskStore.DEALING);
			}else{
				if(oneTask!=null){
					log.info("当前并行任务处理数以达最大");
					fileProcessTaskService.putTask(oneTask, FileProcessTaskService.TaskStore.WAITING);
				}
			}
		} catch (Exception e) {
			if (oneTask != null) {
				WarnMsgUtil.sendEmail("文件批处理任务执行异常", oneTask.getTaskId() + "任务执行出现异常，请查看");
			}
			log.error((oneTask != null ? oneTask.getTaskId() : "") + "任务执行出现异常:", e);
		}
		if (oneTask != null) {
			log.info(oneTask.getTaskId() + "任务执行结束整体流程耗时:" + (System.currentTimeMillis() - startTime) + "毫秒");
		}

	}

	/**
	 * 生成邮件信息
	 * 
	 * @param oneTask
	 * @return
	 */
	private String generContent(FileProcessTask oneTask,AtomicInteger diffCount) {
		StringBuffer buffer = new StringBuffer(oneTask.getTaskId() + "任务执行结束，执行结果：" + oneTask.getDealcode());
		if(diffCount!=null && diffCount.get()>0){
			buffer.append("\n 核查结果不同的手机号数量为[").append(diffCount).append("]个");
		}
		
		buffer.append(Constant.NEW_LINE + "未加密文件的存放路径：" + oneTask.getResuDecryFullFileName());
		buffer.append(Constant.NEW_LINE + "加密文件的存放路径：" + oneTask.getResuEncryFullFileName())
				.append(",加密密码是：" + oneTask.getEncryKey());
		buffer.append(Constant.NEW_LINE+"流程执行类型为:"+RuleTimeService.getRts().getConfigPo(oneTask.getReqMap()).getProcessType().toString());
		return buffer.toString();
	}
}
