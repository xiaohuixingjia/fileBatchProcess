package com.umpay.proxyservice.fileBatch.service.impl;

import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.concurrent.LinkedBlockingQueue;

import com.umpay.proxyservice.fileBatch.bean.FileProcessTask;
import com.umpay.proxyservice.fileBatch.service.FileProcessTaskService;
import com.umpay.proxyservice.util.IdGeneratorUtil;
@Deprecated
public class FileProcessTaskServiceImpl implements FileProcessTaskService {
	private final static Queue<FileProcessTask> queue = new LinkedBlockingQueue<FileProcessTask>(500000);

	@Override
	public boolean putTask(FileProcessTask task, TaskStore taskStore) {
		return queue.offer(task);
	}

	@Override
	public FileProcessTask addNewTask(Map<String, String> reqMap) {
		FileProcessTask task = new FileProcessTask();
		task.setReqMap(reqMap);
		task.setTaskId("fileProcess_" + IdGeneratorUtil.getIdGeneratorUtil().getId());
		return task;
	}

	@Override
	public FileProcessTask getOneTask( TaskStore taskStore) {
		return queue.poll();
	}

	@Override
	public long getTaskSize( TaskStore taskStore) {
		return queue.size();
	}

	@Override
	public void updateTask(FileProcessTask task) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void removeTask(FileProcessTask task,TaskStore taskStore) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public List<String> getTasks(TaskStore taskStore) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public FileProcessTask getTaskById(String taskId) {
		// TODO Auto-generated method stub
		return null;
	}

}
