package com.umpay.proxyservice.fileBatch.tactics.step;

import java.io.File;

import com.umpay.proxyservice.constant.Retcode;
import com.umpay.proxyservice.exception.BaseException;
import com.umpay.proxyservice.fileBatch.bean.FileProcessTask;
import com.umpay.proxyservice.fileBatch.po.ConfigInfoPO;
import com.umpay.proxyservice.processStep.StepInfo;
import com.umpay.proxyservice.ruleTimeService.RuleTimeService;
import com.umpay.proxyservice.util.SftpUtil;

/**
 * 文件sftp上传步骤
 * 
 * @author xuxiaojia
 */
public class SftpUploadStep extends AbsFileProcessStep {

	public SftpUploadStep(StepInfo stepInfo) {
		super(stepInfo);
	}

	@Override
	protected FileProcessTask execute(FileProcessTask oneTask) throws BaseException {
		if (!oneTask.getDealcode().equals(Retcode.SUCCESS.getCode())) {
			return oneTask;
		}
		try {
			ConfigInfoPO configPo = RuleTimeService.getRts().getConfigPo(oneTask.getReqMap());
			String localFullFileName = oneTask.getResuFullFileName();
			String ftpFullFileName = configPo.getFtpUploadPath()
					+ localFullFileName.substring(localFullFileName.lastIndexOf(File.separator) + 1);
			SftpUtil.sshSftpUpload(configPo.getFtpIp(), configPo.getFtpUser(), configPo.getFtpPwd(),
					configPo.getFtpPort(), ftpFullFileName, localFullFileName);
		} catch (Exception e) {
			throw new BaseException(Retcode.FILE_UPLOAD_ERROR, e);
		}
		return oneTask;
	}

}
