package com.umpay.proxyservice.fileBatch.po;

import java.util.Collections;
import java.util.List;

import com.umpay.proxyservice.anno.CheckTypeAnno;
import com.umpay.proxyservice.anno.Require;
import com.umpay.proxyservice.anno.StringCheck;
import com.umpay.proxyservice.handler.ProcessExecuteHandler.ProcessType;

/**
 * 配置信息
 * 
 * @author xuxiaojia
 */
public class ConfigInfoPO {
	/* 功能码 */
	@CheckTypeAnno({ ProcessType.localProcess, ProcessType.sftpProcess, ProcessType.queryCheck })
	@Require
	private String funcode;
	/* 商户号 */
	@CheckTypeAnno({ ProcessType.localProcess, ProcessType.sftpProcess, ProcessType.queryCheck })
	@Require
	private String merid;
	/* 唯一码 */
	@CheckTypeAnno({ ProcessType.localProcess, ProcessType.sftpProcess, ProcessType.queryCheck })
	@Require
	private String license;
	/* ftp的ip */
	@CheckTypeAnno({ ProcessType.sftpProcess })
	@StringCheck(maths = "^((25[0-5]|2[0-4]\\d|[1]{1}\\d{1}\\d{1}|[1-9]{1}\\d{1}|\\d{1})($|(?!\\.$)\\.)){4}$")
	private String ftpIp;
	/* ftp的端口 */
	@CheckTypeAnno({ ProcessType.sftpProcess })
	@Require
	private Integer ftpPort;
	/* ftp的用户 */
	@CheckTypeAnno({ ProcessType.sftpProcess })
	@Require
	private String ftpUser;
	/* ftp的密码 */
	@CheckTypeAnno({ ProcessType.sftpProcess })
	@Require
	private String ftpPwd;
	/* ftp的上传路径 */
	@CheckTypeAnno({ ProcessType.sftpProcess })
	@Require
	private String ftpUploadPath;
	/* ftp的下载路径 */
	@CheckTypeAnno({ ProcessType.sftpProcess })
	@Require
	private String ftpDownloadPath;
	/* 请求文件最小数量 */
	@CheckTypeAnno({ ProcessType.localProcess, ProcessType.sftpProcess, ProcessType.queryCheck })
	@Require
	private int fileMinSize;
	/* 请求文件最大数量 */
	@CheckTypeAnno({ ProcessType.localProcess, ProcessType.sftpProcess, ProcessType.queryCheck })
	@Require
	private int fileMaxSize;
	/* 最大的tps请求 */
	@CheckTypeAnno({ ProcessType.localProcess, ProcessType.sftpProcess, ProcessType.queryCheck })
	@Require
	private int maxTps;
	/* 请求数据处理的线程数 */
	private int queryThreads;
	/* 是否需要加密 */
	private boolean needEncry;
	/* 加密秘钥 必须是16位 */
	@CheckTypeAnno({ ProcessType.sftpProcess })
	@StringCheck(equalLength = 16, mustHave = false)
	private String encryKey;
	/* AES加密的16位字符信息 */
	@CheckTypeAnno({ ProcessType.sftpProcess })
	@StringCheck(equalLength = 16)
	private String ivString;
	/* 通知的url路径 */
	@CheckTypeAnno({ ProcessType.sftpProcess })
	@Require
	private String noticeUrl;
	/* 文件里的必输项 */
	@CheckTypeAnno({ ProcessType.localProcess, ProcessType.sftpProcess, ProcessType.queryCheck })
	@Require
	private List<String> fileMustHaveField;
	/* 授权访问的ip */
	@CheckTypeAnno({ ProcessType.sftpProcess })
	@Require
	private List<String> empowerIps;
	/* 请求报文的必输项 */
	@CheckTypeAnno({ ProcessType.localProcess, ProcessType.sftpProcess, ProcessType.queryCheck })
	@Require
	private List<String> reqFieldMustHaveList;
	/* 请求报文的签名校验列表 */
	@CheckTypeAnno({ ProcessType.sftpProcess })
	@Require
	private List<String> reqSignCheckList;
	/* 响应报文的签名校验列表 */
	@CheckTypeAnno({ ProcessType.sftpProcess })
	@Require
	private List<String> respSignCheckList;
	/* 请求报文的签名生成列表 */
	@CheckTypeAnno({ ProcessType.sftpProcess })
	@Require
	private List<String> reqSignCreateList;
	/* 响应报文的签名生成列表 */
	@CheckTypeAnno({ ProcessType.sftpProcess })
	@Require
	private List<String> respSignCreateList;
	/* 查询数据的路径 */
	@CheckTypeAnno({ ProcessType.localProcess, ProcessType.sftpProcess, ProcessType.queryCheck })
	@Require
	private String queryDataUrl;
	/* 查询数据的路径 */
	@CheckTypeAnno({ ProcessType.queryCheck })
	@Require
	private String checkDataUrl;
	/* 定制转换类 */
	private String transferResponseClass;
	/* 流程处理类型 */
	@CheckTypeAnno({ ProcessType.localProcess, ProcessType.sftpProcess, ProcessType.queryCheck })
	@Require
	private ProcessType processType;
	/* 从任务请求信息中获取元素用于拼接请求数据的xml报文 */
	@CheckTypeAnno({ ProcessType.localProcess, ProcessType.sftpProcess, ProcessType.queryCheck })
	@Require
	private List<String> elemsFromQueryXml4QueryData;
	/* 子商户号 */
	private String childmerid;

	public String getCheckDataUrl() {
		return checkDataUrl;
	}

	public void setCheckDataUrl(String checkDataUrl) {
		this.checkDataUrl = checkDataUrl;
	}

	public ConfigInfoPO() {
		super();
	}

	public String getChildmerid() {
		return childmerid;
	}

	public void setChildmerid(String childmerid) {
		this.childmerid = childmerid;
	}

	public int getQueryThreads() {
		return queryThreads;
	}

	public void setQueryThreads(int queryThreads) {
		this.queryThreads = queryThreads;
	}

	public String getFuncode() {
		return funcode;
	}

	public void setFuncode(String funcode) {
		this.funcode = funcode;
	}

	public String getMerid() {
		return merid;
	}

	public void setMerid(String merid) {
		this.merid = merid;
	}

	public String getLicense() {
		return license;
	}

	public void setLicense(String license) {
		this.license = license;
	}

	public String getFtpIp() {
		return ftpIp;
	}

	public void setFtpIp(String ftpIp) {
		this.ftpIp = ftpIp;
	}

	public Integer getFtpPort() {
		return ftpPort;
	}

	public void setFtpPort(Integer ftpPort) {
		this.ftpPort = ftpPort;
	}

	public String getFtpUser() {
		return ftpUser;
	}

	public void setFtpUser(String ftpUser) {
		this.ftpUser = ftpUser;
	}

	public String getFtpPwd() {
		return ftpPwd;
	}

	public void setFtpPwd(String ftpPwd) {
		this.ftpPwd = ftpPwd;
	}

	public String getFtpUploadPath() {
		return ftpUploadPath;
	}

	public void setFtpUploadPath(String ftpUploadPath) {
		this.ftpUploadPath = ftpUploadPath;
	}

	public String getFtpDownloadPath() {
		return ftpDownloadPath;
	}

	public void setFtpDownloadPath(String ftpDownloadPath) {
		this.ftpDownloadPath = ftpDownloadPath;
	}

	public int getFileMinSize() {
		return fileMinSize;
	}

	public void setFileMinSize(int fileMinSize) {
		this.fileMinSize = fileMinSize;
	}

	public int getFileMaxSize() {
		return fileMaxSize;
	}

	public void setFileMaxSize(int fileMaxSize) {
		this.fileMaxSize = fileMaxSize;
	}

	public int getMaxTps() {
		return maxTps;
	}

	public void setMaxTps(int maxTps) {
		this.maxTps = maxTps;
	}

	public boolean isNeedEncry() {
		return needEncry;
	}

	public void setNeedEncry(boolean needEncry) {
		this.needEncry = needEncry;
	}

	public String getEncryKey() {
		return encryKey;
	}

	public void setEncryKey(String encryKey) {
		this.encryKey = encryKey;
	}

	public String getIvString() {
		return ivString;
	}

	public void setIvString(String ivString) {
		this.ivString = ivString;
	}

	public String getNoticeUrl() {
		return noticeUrl;
	}

	public void setNoticeUrl(String noticeUrl) {
		this.noticeUrl = noticeUrl;
	}

	public List<String> getFileMustHaveField() {
		return fileMustHaveField;
	}

	public void setFileMustHaveField(List<String> fileMustHaveField) {
		this.fileMustHaveField = fileMustHaveField;
	}

	public List<String> getEmpowerIps() {
		return empowerIps;
	}

	public void setEmpowerIps(List<String> empowerIps) {
		this.empowerIps = empowerIps;
	}

	public List<String> getReqFieldMustHaveList() {
		return reqFieldMustHaveList;
	}

	public void setReqFieldMustHaveList(List<String> reqFieldMustHaveList) {
		this.reqFieldMustHaveList = reqFieldMustHaveList;
	}

	public List<String> getReqSignCheckList() {
		return reqSignCheckList;
	}

	public void setReqSignCheckList(List<String> reqSignCheckList) {
		this.reqSignCheckList = reqSignCheckList;
	}

	public List<String> getRespSignCheckList() {
		return respSignCheckList;
	}

	public void setRespSignCheckList(List<String> respSignCheckList) {
		this.respSignCheckList = respSignCheckList;
	}

	public List<String> getReqSignCreateList() {
		return reqSignCreateList;
	}

	public void setReqSignCreateList(List<String> reqSignCreateList) {
		this.reqSignCreateList = reqSignCreateList;
	}

	public List<String> getRespSignCreateList() {
		return respSignCreateList;
	}

	public void setRespSignCreateList(List<String> respSignCreateList) {
		this.respSignCreateList = respSignCreateList;
	}

	public String getQueryDataUrl() {
		return queryDataUrl;
	}

	public void setQueryDataUrl(String queryDataUrl) {
		this.queryDataUrl = queryDataUrl;
	}

	public String getTransferResponseClass() {
		return transferResponseClass;
	}

	public void setTransferResponseClass(String transferResponseClass) {
		this.transferResponseClass = transferResponseClass;
	}

	public ProcessType getProcessType() {
		return processType;
	}

	public void setProcessType(ProcessType processType) {
		this.processType = processType;
	}

	public List<String> getElemsFromQueryXml4QueryData() {
		return elemsFromQueryXml4QueryData;
	}

	public void setElemsFromQueryXml4QueryData(List<String> elemsFromQueryXml4QueryData) {
		this.elemsFromQueryXml4QueryData = elemsFromQueryXml4QueryData;
	}

	public ConfigInfoPO(ConfigInfoBuilder builder) {
		this.funcode = builder.funcode;
		this.merid = builder.merid;
		this.license = builder.license;
		this.ftpIp = builder.ftpIp;
		this.ftpPort = builder.ftpPort;
		this.ftpUser = builder.ftpUser;
		this.ftpPwd = builder.ftpPwd;
		this.ftpUploadPath = builder.ftpUploadPath;
		this.ftpDownloadPath = builder.ftpDownloadPath;
		this.fileMinSize = builder.fileMinSize;
		this.fileMaxSize = builder.fileMaxSize;
		this.needEncry = builder.needEncry;
		this.encryKey = builder.encryKey;
		this.ivString = builder.ivString;
		this.reqFieldMustHaveList = builder.reqFieldMustHaveList;
		this.reqSignCheckList = builder.reqSignCheckList;
		this.respSignCheckList = builder.respSignCheckList;
		this.reqSignCreateList = builder.reqSignCreateList;
		this.respSignCreateList = builder.respSignCreateList;
		this.fileMustHaveField = builder.fileMustHaveField;
		this.noticeUrl = builder.noticeUrl;
		this.empowerIps = builder.empowerIps;
		this.maxTps = builder.maxTps;
		this.queryThreads = builder.queryThreads;
		this.queryDataUrl = builder.queryDataUrl;
		this.transferResponseClass = builder.transferResponseClass;
		this.processType = builder.processType;
		this.elemsFromQueryXml4QueryData = builder.elemsFromQueryXml4QueryData;
		this.childmerid = builder.childmerid;
		this.checkDataUrl = builder.checkDataUrl;
	}

	public static class ConfigInfoBuilder {
		private String checkDataUrl;
		private String funcode;
		private String merid;
		private String license;
		private String ftpIp;
		private int ftpPort;
		private String ftpUser;
		private String ftpPwd;
		private String ftpUploadPath;
		private String ftpDownloadPath;
		private int fileMinSize;
		private int fileMaxSize;
		private boolean needEncry;
		private String encryKey;
		private String ivString;
		private List<String> reqFieldMustHaveList;
		private List<String> reqSignCheckList;
		private List<String> respSignCheckList;
		private List<String> reqSignCreateList;
		private List<String> respSignCreateList;
		private List<String> fileMustHaveField;
		private List<String> empowerIps;
		private String noticeUrl;
		private String queryDataUrl;
		private String transferResponseClass;
		private int maxTps;
		private int queryThreads;
		private ProcessType processType;
		public List<String> elemsFromQueryXml4QueryData;
		private String childmerid;

		public ConfigInfoBuilder setCheckDataUrl(String checkDataUrl) {
			this.checkDataUrl = checkDataUrl;
			return this;
		}

		public ConfigInfoBuilder setChildmerid(String childmerid) {
			this.childmerid = childmerid;
			return this;
		}

		public ConfigInfoBuilder setQueryThreads(int queryThreads) {
			this.queryThreads = queryThreads;
			return this;
		}

		public ConfigInfoBuilder setProcessType(ProcessType processType) {
			this.processType = processType;
			return this;
		}

		public ConfigInfoBuilder setTransferResponseClass(String transferResponseClass) {
			this.transferResponseClass = transferResponseClass;
			return this;
		}

		public ConfigInfoBuilder setIvString(String ivString) {
			this.ivString = ivString;
			return this;
		}

		public ConfigInfoBuilder setQueryDataUrl(String queryDataUrl) {
			this.queryDataUrl = queryDataUrl;
			return this;
		}

		public ConfigInfoBuilder setMaxTps(int maxTps) {
			this.maxTps = maxTps;
			return this;
		}

		public ConfigInfoBuilder setNoticeUrl(String noticeUrl) {
			this.noticeUrl = noticeUrl;
			return this;
		}

		public ConfigInfoBuilder setFuncode(String funcode) {
			this.funcode = funcode;
			return this;
		}

		public ConfigInfoBuilder setMerid(String merid) {
			this.merid = merid;
			return this;
		}

		public ConfigInfoBuilder setLicense(String license) {
			this.license = license;
			return this;
		}

		public ConfigInfoBuilder setFtpIp(String ftpIp) {
			this.ftpIp = ftpIp;
			return this;
		}

		public ConfigInfoBuilder setFtpPort(int ftpPort) {
			this.ftpPort = ftpPort;
			return this;
		}

		public ConfigInfoBuilder setFtpUser(String ftpUser) {
			this.ftpUser = ftpUser;
			return this;
		}

		public ConfigInfoBuilder setFtpPwd(String ftpPwd) {
			this.ftpPwd = ftpPwd;
			return this;
		}

		public ConfigInfoBuilder setFtpUploadPath(String ftpUploadPath) {
			this.ftpUploadPath = ftpUploadPath;
			return this;
		}

		public ConfigInfoBuilder setFtpDownloadPath(String ftpDownloadPath) {
			this.ftpDownloadPath = ftpDownloadPath;
			return this;
		}

		public ConfigInfoBuilder setFileMinSize(int fileMinSize) {
			this.fileMinSize = fileMinSize;
			return this;
		}

		public ConfigInfoBuilder setFileMaxSize(int fileMaxSize) {
			this.fileMaxSize = fileMaxSize;
			return this;
		}

		public ConfigInfoBuilder setNeedEncry(boolean needEncry) {
			this.needEncry = needEncry;
			return this;
		}

		public ConfigInfoBuilder setEncryKey(String encryKey) {
			this.encryKey = encryKey;
			return this;
		}

		public ConfigInfoBuilder setFileMustHaveField(List<String> fileMustHaveField) {
			this.fileMustHaveField = fileMustHaveField;
			return this;
		}

		public ConfigInfoBuilder setReqFieldMustHaveList(List<String> reqFieldMustHaveList) {
			this.reqFieldMustHaveList = reqFieldMustHaveList;
			return this;
		}

		public ConfigInfoBuilder setReqSignCheckList(List<String> reqSignCheckList) {
			this.reqSignCheckList = reqSignCheckList;
			return this;
		}

		public ConfigInfoBuilder setRespSignCheckList(List<String> respSignCheckList) {
			this.respSignCheckList = respSignCheckList;
			return this;
		}

		public ConfigInfoBuilder setReqSignCreateList(List<String> reqSignCreateList) {
			this.reqSignCreateList = reqSignCreateList;
			return this;
		}

		public ConfigInfoBuilder setRespSignCreateList(List<String> respSignCreateList) {
			this.respSignCreateList = respSignCreateList;
			return this;
		}

		public ConfigInfoBuilder setElemsFromQueryXml4QueryData(List<String> elemsFromQueryXml4QueryData) {
			this.elemsFromQueryXml4QueryData = elemsFromQueryXml4QueryData;
			return this;
		}

		public ConfigInfoBuilder setEmpowerIps(List<String> empowerIps) {
			this.empowerIps = empowerIps;
			return this;
		}

		public ConfigInfoPO build() {
			return new ConfigInfoPO(this);
		}
	}

	public void init() {
		if (reqSignCheckList != null) {
			Collections.sort(this.reqSignCheckList);
		}
		if (respSignCheckList != null) {
			Collections.sort(this.respSignCheckList);
		}
		if (reqSignCreateList != null) {
			Collections.sort(this.reqSignCreateList);
		}
		if (respSignCreateList != null) {
			Collections.sort(this.respSignCreateList);
		}
	}

}
