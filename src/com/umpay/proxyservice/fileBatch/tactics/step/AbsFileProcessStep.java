package com.umpay.proxyservice.fileBatch.tactics.step;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.umpay.proxyservice.constant.Retcode;
import com.umpay.proxyservice.exception.BaseException;
import com.umpay.proxyservice.fileBatch.bean.FileProcessTask;
import com.umpay.proxyservice.fileBatch.service.FileProcessTaskService;
import com.umpay.proxyservice.processStep.AbsProcessStep;
import com.umpay.proxyservice.processStep.StepInfo;
import com.umpay.proxyservice.util.SpringUtil;

/**
 * 文件流程处理
 * 
 * @author xuxiaojia
 */
public abstract class AbsFileProcessStep extends AbsProcessStep<FileProcessTask> {
	private final static Logger log = LoggerFactory.getLogger("AbsFileProcessStep");

	public AbsFileProcessStep(StepInfo stepInfo) {
		super(stepInfo);
	}

	@Override
	protected boolean isCurrStepProcess(FileProcessTask t) {
		return t.getStepInfo().equals(this.getStepInfo());
	}

	@Override
	protected FileProcessTask doProcess(FileProcessTask t) {
		long startTime = System.currentTimeMillis();
		try {
			log.info(t.getTaskId() + "当前处理结果码为:" + t.getDealcode() + "执行到流程：" + getStepInfo());
			SpringUtil.getInstance().getContext().getBean(FileProcessTaskService.class).updateTask(t);
			t = execute(t);
		} catch (BaseException e) {
			t.setDealcode(e.getRetcode().getCode());
			log.error(t.getTaskId() + "执行" + this.getStepInfo().getStepName() + "时未通过该流程", e);
		} catch (Exception e) {
			t.setDealcode(Retcode.INNER_ERROR.getCode());
			log.error(t.getTaskId() + "执行" + this.getStepInfo().getStepName() + "时未通过该流程", e);
		}
		log.info(t.getTaskId() + "执行"+getStepInfo()+"流程耗时：" + (System.currentTimeMillis() - startTime));
		return t;
	}

	@Override
	protected FileProcessTask putNextStep2task(StepInfo stepInfo, FileProcessTask t) {
		t.setStepInfo(stepInfo);
		return t;
	}

	protected abstract FileProcessTask execute(FileProcessTask t) throws BaseException;
}
