package com.umpay.proxyservice.fileBatch.service;

import java.util.List;
import java.util.Map;

import com.umpay.proxyservice.fileBatch.bean.FileProcessTask;
import com.umpay.proxyservice.util.PropertiesUtil;

/**
 * 文件批处理服务接口
 * 
 * @author xuxiaojia
 */
public interface FileProcessTaskService {
	/**
	 * 将处理任务放入指定仓库
	 * 
	 * @param task
	 *            任务对象
	 * @param taskStroe
	 *            任务存储仓库
	 * @return
	 */
	public boolean putTask(FileProcessTask task, TaskStore taskStore) throws Exception;

	/**
	 * 新增文件处理任务
	 * 
	 * @param reqMap
	 *            请求信息解析后的map
	 * @return
	 */
	public FileProcessTask addNewTask(Map<String, String> reqMap) throws Exception;

	/**
	 * 更新task信息
	 * 
	 * @param task
	 */
	public void updateTask(FileProcessTask task) throws Exception;

	/**
	 * 获取一个待处理任务 如果没有则返回null
	 * 
	 * @param taskStroe
	 *            任务存储仓库
	 * @return
	 */
	public FileProcessTask getOneTask(TaskStore taskStore) throws Exception;

	/**
	 * 获取待处理任务的数量
	 * 
	 * @param taskStroe
	 *            任务存储仓库
	 * @return
	 */
	public long getTaskSize(TaskStore taskStore) throws Exception;

	/**
	 * 删除任务
	 * 
	 * @param task
	 *            任务对象
	 * @param taskStroe
	 *            任务存储仓库
	 */
	public void removeTask(FileProcessTask task, TaskStore taskStore) throws Exception;

	/**
	 * 获取存储仓库里的任务id
	 * 
	 * @param taskStore
	 * @return
	 */
	public List<String> getTasks(TaskStore taskStore);

	/**
	 * 根据任务id获取任务信息
	 * 
	 * @param taskId
	 * @return
	 */
	public FileProcessTask getTaskById(String taskId)  throws Exception;
 
	/**
	 * 任务存储仓库类型
	 * 
	 * @author xuxiaojia
	 */
	enum TaskStore {
		/**
		 * 待处理仓库
		 */
		WAITING {
			private String name = PropertiesUtil.getInstance("config.properties")
					.getConfigItem("redisWaitingTaskQueue");

			@Override
			public String getStoreName() {
				return name;
			}

		},
		/**
		 * 正在处理仓库
		 */
		DEALING {
			private String name = PropertiesUtil.getInstance("config.properties")
					.getConfigItem("redisDealingTaskQueue");

			@Override
			public String getStoreName() {
				return name;
			};
		};

		/**
		 * 获取仓库名称
		 * 
		 * @return
		 */
		public abstract String getStoreName();

	}
}
