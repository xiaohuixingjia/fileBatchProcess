package com.umpay.proxyservice.fileBatch.tactics.step;

import java.io.File;

import org.apache.commons.io.FileUtils;

import com.umpay.proxyservice.constant.Retcode;
import com.umpay.proxyservice.exception.BaseException;
import com.umpay.proxyservice.fileBatch.bean.FileProcessTask;
import com.umpay.proxyservice.fileBatch.util.GenerateUtil;
import com.umpay.proxyservice.processStep.StepInfo;
import com.umpay.proxyservice.util.FileUtil;
import com.umpay.proxyservice.util.HttpMap;

/**
 * 本地文件处理 第一步
 * 
 * @author xuxiaojia
 */
public class LocalFileFirstStep extends AbsFileProcessStep {

	public LocalFileFirstStep(StepInfo stepInfo) {
		super(stepInfo);
	}

	@Override
	protected FileProcessTask execute(FileProcessTask oneTask) throws BaseException {
		String reqFullFileName = oneTask.getReqMap().get(HttpMap.REQ_FILE_PATH);
		if (FileUtil.fileExist(reqFullFileName)) {
			copyFile(oneTask, reqFullFileName);
			return oneTask;
		} else {
			throw new BaseException(Retcode.FILE_NOT_FOUND);
		}
	}

	private void copyFile(FileProcessTask oneTask, String reqFullFileName) throws BaseException {
		try {
			// 下载文件的全路径
			String localFilePath = GenerateUtil.getDownloadPath(oneTask.getTaskId(),
					oneTask.getReqMap().get(HttpMap.FUNCODE), oneTask.getReqMap().get(HttpMap.MERID));
			String localFullFileName = localFilePath + oneTask.getReqMap().get(HttpMap.FILE);
			// 创建文件夹
			FileUtil.mkdirs(localFilePath);
			FileUtils.copyFile(new File(reqFullFileName), new File(localFullFileName));
			oneTask.setLocalFullFileName(localFullFileName);
		} catch (Exception e) {
			throw new BaseException(Retcode.FILE_COPY_ERROR, e);
		}
	}

}
