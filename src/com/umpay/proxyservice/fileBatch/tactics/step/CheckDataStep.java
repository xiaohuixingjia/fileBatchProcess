package com.umpay.proxyservice.fileBatch.tactics.step;

import com.umpay.proxyservice.constant.Retcode;
import com.umpay.proxyservice.exception.BaseException;
import com.umpay.proxyservice.fileBatch.bean.FileProcessTask;
import com.umpay.proxyservice.fileBatch.tactics.fileAnalz.FileAnalzTactics;
import com.umpay.proxyservice.fileBatch.tactics.fileAnalz.impl.SimpleFileAbstractTatics;
import com.umpay.proxyservice.fileBatch.tactics.writer.InfoWriteTatics;
import com.umpay.proxyservice.fileBatch.tactics.writer.impl.CheckDataWriter;
import com.umpay.proxyservice.processStep.StepInfo;
/**
 * 文件处理 核查数据是否一致步骤
* @author xuxiaojia
 */
public class CheckDataStep extends AbsFileProcessStep {

	public CheckDataStep(StepInfo stepInfo) {
		super(stepInfo);
	}

	@Override
	protected FileProcessTask execute(FileProcessTask oneTask) throws BaseException {
		if (!oneTask.getDealcode().equals(Retcode.SUCCESS.getCode())) {
			return oneTask;
		}
		FileAnalzTactics<FileProcessTask> fileAnalz = new SimpleFileAbstractTatics();
		InfoWriteTatics<FileProcessTask> writeTatics = new CheckDataWriter();
		return fileAnalz.analz(oneTask, writeTatics);
	}

}
