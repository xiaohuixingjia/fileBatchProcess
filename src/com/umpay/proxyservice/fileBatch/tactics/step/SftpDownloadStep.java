package com.umpay.proxyservice.fileBatch.tactics.step;

import com.umpay.proxyservice.constant.Retcode;
import com.umpay.proxyservice.exception.BaseException;
import com.umpay.proxyservice.fileBatch.bean.FileProcessTask;
import com.umpay.proxyservice.fileBatch.po.ConfigInfoPO;
import com.umpay.proxyservice.fileBatch.util.GenerateUtil;
import com.umpay.proxyservice.processStep.StepInfo;
import com.umpay.proxyservice.ruleTimeService.RuleTimeService;
import com.umpay.proxyservice.util.FileUtil;
import com.umpay.proxyservice.util.HttpMap;
import com.umpay.proxyservice.util.SftpUtil;

/**
 * 文件sftp下载步骤
 * 
 * @author xuxiaojia
 */
public class SftpDownloadStep extends AbsFileProcessStep {

	public SftpDownloadStep(StepInfo stepInfo) {
		super(stepInfo);
	}

	@Override
	protected FileProcessTask execute(FileProcessTask oneTask) throws BaseException {
		try {
			ConfigInfoPO configPo = RuleTimeService.getRts().getConfigPo(oneTask.getReqMap());
			// 下载文件的全路径
			String ftpDownLoadPath = configPo.getFtpDownloadPath() + oneTask.getReqMap().get(HttpMap.FILE);
			String localFilePath = GenerateUtil.getDownloadPath(oneTask.getTaskId(),
					oneTask.getReqMap().get(HttpMap.FUNCODE), oneTask.getReqMap().get(HttpMap.MERID));
			String localFullFileName = localFilePath + oneTask.getReqMap().get(HttpMap.FILE);
			// 创建文件夹
			FileUtil.mkdirs(localFilePath);
		SftpUtil.sshSftpDownload(configPo.getFtpIp(), configPo.getFtpUser(), configPo.getFtpPwd(),
				configPo.getFtpPort(), ftpDownLoadPath, localFullFileName);
			oneTask.setLocalFullFileName(localFullFileName);
		} catch (Exception e) {
			throw new BaseException(Retcode.FILE_DOWNLOAD_ERROR,e);
		}
		return oneTask;
	}

}
