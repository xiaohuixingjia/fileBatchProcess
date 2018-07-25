package com.umpay.proxyservice.fileBatch.service.impl;

import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;

import com.umpay.proxyservice.constant.Retcode;
import com.umpay.proxyservice.fileBatch.bean.FileProcessTask;
import com.umpay.proxyservice.fileBatch.service.FileProcessTaskService;
import com.umpay.proxyservice.redis.RedisClient;
import com.umpay.proxyservice.util.IdGeneratorUtil;
import com.umpay.proxyservice.util.JacksonUtil;

public class FileProcessTaskRedisServiceImpl implements FileProcessTaskService {

	@Autowired
	private RedisClient redisClient;

	@Override
	public boolean putTask(FileProcessTask task, TaskStore taskStore) throws Exception {
		redisClient.set(task.getTaskId(), JacksonUtil.obj2json(task));
		redisClient.lPush(taskStore.getStoreName(), task.getTaskId());
		return true;
	}

	@Override
	public FileProcessTask addNewTask(Map<String, String> reqMap) throws Exception {
		FileProcessTask task = new FileProcessTask();
		task.setReqMap(reqMap);
		task.setDealcode(Retcode.SUCCESS.getCode());
		task.setTaskId("fileProcess_" + IdGeneratorUtil.getIdGeneratorUtil().getId());
		putTask(task, FileProcessTaskService.TaskStore.WAITING);
		return task;
	}

	@Override
	public FileProcessTask getOneTask(TaskStore taskStore) throws Exception {
		String taskId = redisClient.rpop(taskStore.getStoreName());
		if (StringUtils.isNotEmpty(taskId)) {
			FileProcessTask jsonString2Obj = JacksonUtil.jsonString2Obj(redisClient.get(taskId), FileProcessTask.class);
			return jsonString2Obj;
		}
		return null;
	}

	@Override
	public long getTaskSize(TaskStore taskStore) {
		return redisClient.listSize(taskStore.getStoreName());
	}

	@Override
	public void updateTask(FileProcessTask task) throws Exception {
		redisClient.set(task.getTaskId(), JacksonUtil.obj2json(task));
	}

	@Override
	public void removeTask(FileProcessTask task, TaskStore taskStore) throws Exception {
		// 将该任务保存15天
		redisClient.setEx(task.getTaskId(), JacksonUtil.obj2json(task));
		redisClient.lrem(taskStore.getStoreName(), task.getTaskId());
	}

	@Override
	public List<String> getTasks(TaskStore taskStore) {
		return redisClient.lrang(taskStore.getStoreName());
	}

	@Override
	public FileProcessTask getTaskById(String taskId) throws Exception {
		return JacksonUtil.jsonString2Obj(redisClient.get(taskId), FileProcessTask.class);
	}

}
