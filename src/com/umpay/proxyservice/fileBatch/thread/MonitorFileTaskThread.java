package com.umpay.proxyservice.fileBatch.thread;

import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.umpay.proxyservice.fileBatch.bean.FileProcessTask;
import com.umpay.proxyservice.fileBatch.service.FileProcessTaskService;
import com.umpay.proxyservice.handler.ProcessExecuteHandler;

/**
 * 监控文件处理任务
 * 
 * @author xuxiaojia
 */
public class MonitorFileTaskThread {
	private static final Logger log = LoggerFactory.getLogger(MonitorFileTaskThread.class);
	@Autowired
	private FileProcessTaskService fileProcessTaskService;

	public void execute() {
		List<String> tasks = fileProcessTaskService.getTasks(FileProcessTaskService.TaskStore.DEALING);
		if (CollectionUtils.isEmpty(tasks)) {
			log.info("当前没有正在执行的任务");
			return;
		}
		log.info("当前正在执行的任务有："+ProcessExecuteHandler.getInstance().currentRunTaskNum());
		for (String string : tasks) {
			try {
				FileProcessTask task = fileProcessTaskService.getTaskById(string);
				if (task == null) {
					continue;
				}
				log.info("监控："+task.getTaskId()+"正在执行："+task.getStepInfo());
			} catch (Exception e) {
			}
		}
	}
}
