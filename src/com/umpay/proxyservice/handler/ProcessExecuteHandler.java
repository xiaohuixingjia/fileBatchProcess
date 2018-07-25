package com.umpay.proxyservice.handler;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

import com.umpay.proxyservice.fileBatch.bean.FileProcessTask;
import com.umpay.proxyservice.fileBatch.tactics.step.AbsFileProcessStep;
import com.umpay.proxyservice.fileBatch.tactics.step.CallBackNoticeStep;
import com.umpay.proxyservice.fileBatch.tactics.step.CheckDataStep;
import com.umpay.proxyservice.fileBatch.tactics.step.CommonFileCheckStep;
import com.umpay.proxyservice.fileBatch.tactics.step.LocalFileFirstStep;
import com.umpay.proxyservice.fileBatch.tactics.step.QueryDataStep;
import com.umpay.proxyservice.fileBatch.tactics.step.SftpDownloadStep;
import com.umpay.proxyservice.fileBatch.tactics.step.SftpUploadStep;
import com.umpay.proxyservice.processStep.StepInfo;
import com.umpay.proxyservice.ruleTimeService.RuleTimeService;
import com.umpay.proxyservice.util.CastUtil;
import com.umpay.proxyservice.util.PropertiesUtil;

/**
 * 流程处理者
 * 
 * @author xuxiaojia
 */
public class ProcessExecuteHandler {

	private final static AtomicInteger taskNum = new AtomicInteger(0);

	private Map<ProcessType, AbsFileProcessStep> processMapping;

	private static ProcessExecuteHandler handler = new ProcessExecuteHandler();

	public static ProcessExecuteHandler getInstance() {
		return handler;
	}

	private ProcessExecuteHandler() {
		processMapping = new HashMap<ProcessExecuteHandler.ProcessType, AbsFileProcessStep>();
		AbsFileProcessStep sftpProcess = new SftpDownloadStep(new StepInfo("文件sftp下载", 1));
		sftpProcess.addProcessStep(new CommonFileCheckStep(new StepInfo("文件内容校验", 2)));
		sftpProcess.addProcessStep(new QueryDataStep(new StepInfo("文件处理获取数据", 3)));
		sftpProcess.addProcessStep(new SftpUploadStep(new StepInfo("结果文件sftp上传", 4)));
		sftpProcess.addProcessStep(new CallBackNoticeStep(new StepInfo("回调通知商户", 5)));
		processMapping.put(ProcessType.sftpProcess, sftpProcess);
		AbsFileProcessStep localProcess = new LocalFileFirstStep(new StepInfo("本地文件信息确认", 1));
		localProcess.addProcessStep(new CommonFileCheckStep(new StepInfo("文件内容校验", 2)));
		localProcess.addProcessStep(new QueryDataStep(new StepInfo("文件处理获取数据", 3)));
		processMapping.put(ProcessType.localProcess, localProcess);
		AbsFileProcessStep queryCheck = new LocalFileFirstStep(new StepInfo("本地文件信息确认", 1));
		queryCheck.addProcessStep(new CommonFileCheckStep(new StepInfo("文件内容校验", 2)));
		queryCheck.addProcessStep(new CheckDataStep(new StepInfo("文件处理开始校验数据", 3)));
		processMapping.put(ProcessType.queryCheck, queryCheck);
	}

	/**
	 * 返回当前正在执行的任务数量
	 * 
	 * @return
	 */
	public int currentRunTaskNum() {
		return taskNum.get();
	}

	/**
	 * 链表式执行文件批处理任务
	 * 
	 * @param oneTask
	 */
	public void linkedExecute(FileProcessTask oneTask) {
		taskNum.incrementAndGet();
		AbsFileProcessStep processStep = null;
		if (RuleTimeService.getRts().getConfigPo(oneTask.getReqMap()).getProcessType() == null) {
			processStep = processMapping.get(ProcessType.localProcess);
		} else {
			processStep = processMapping
					.get(RuleTimeService.getRts().getConfigPo(oneTask.getReqMap()).getProcessType());
		}
		if (oneTask.getStepInfo() == null) {
			oneTask.setStepInfo(processStep.getStepInfo());
		}
		processStep.linkedProcess(oneTask);
		taskNum.decrementAndGet();
	}

	/**
	 * 判断当前任务流程可以被处理
	 * 
	 * @param oneTask
	 * @return
	 */
	public boolean thisTaskCanProcess(FileProcessTask oneTask) {
		// 判断当前同时执行的任务数是否超量
		if (taskNum.get() >= CastUtil.string2int(
				PropertiesUtil.getInstance("config.properties").getConfigItem("maxTaskRunNum"), 3)) {
			return false;
		}
		return true;
	}

	/**
	 * 流程处理类型 sftp下载回调处理、本地文件处理
	 * 
	 * @author xuxiaojia
	 */
	public enum ProcessType {
		sftpProcess, localProcess,queryCheck
	}
}
