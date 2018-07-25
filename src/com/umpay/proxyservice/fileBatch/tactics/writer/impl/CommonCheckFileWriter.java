package com.umpay.proxyservice.fileBatch.tactics.writer.impl;

import java.util.Arrays;
import java.util.List;

import org.apache.commons.lang.StringUtils;

import com.umpay.proxyservice.Constant;
import com.umpay.proxyservice.constant.Retcode;
import com.umpay.proxyservice.exception.BaseException;
import com.umpay.proxyservice.fileBatch.bean.FileProcessTask;
import com.umpay.proxyservice.fileBatch.tactics.writer.InfoWriterAbstractTatics;
import com.umpay.proxyservice.fileBatch.util.GenerateUtil;
import com.umpay.proxyservice.ruleTimeService.RuleTimeService;
import com.umpay.proxyservice.util.HttpMap;

public class CommonCheckFileWriter extends InfoWriterAbstractTatics<FileProcessTask> {
	private FileProcessTask fileProcessTask;
	private int currentLineNum;

	@Override
	public void init(FileProcessTask fileInfo) throws BaseException {
		fileProcessTask =  fileInfo;
	}

	@Override
	public void writeIn(String lineInfo, int lineNum) throws BaseException {
		if (lineNum == 1) {
			if (StringUtils.isEmpty(lineInfo)) {
				throw new BaseException(Retcode.FILE_FIRST_LINE_ERROR, "文件输入项为空");
			}
			lineInfo=GenerateUtil.utf8FirstStrChange(lineInfo);
			List<String> reqElems = Arrays.asList(lineInfo.split(Constant.LOG_SEPARATOR));
			List<String> fileMustHaveElems = RuleTimeService.getRts().getConfigPo(fileProcessTask.getReqMap())
					.getFileMustHaveField();
			for (String string : fileMustHaveElems) {
				if (!reqElems.contains(string)) {
					throw new BaseException(Retcode.FILE_FIRST_LINE_ERROR, "必输项:" + string + "不在文件中");
				}
			}
		}
		currentLineNum = lineNum;
	}

	@Override
	public FileProcessTask end(boolean readEnd) throws BaseException {
		// 如果是读取完毕则判断文件行数
		if(readEnd){
			if (currentLineNum != Integer.parseInt(fileProcessTask.getReqMap().get(HttpMap.SIZE))) {
				throw new BaseException(Retcode.FILE_SIZE_ERROR,
						"实际行数:" + currentLineNum + " 请求声明的行数:" + fileProcessTask.getReqMap().get(HttpMap.SIZE));
			}
		}
		return this.fileProcessTask;
	}

}
