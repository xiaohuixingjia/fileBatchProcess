package com.umpay.proxyservice.fileBatch.bean;

/**
 * 文件信息
 * 
 * @author xuxiaojia
 */
public class FileInfo {
	/**
	 * 本地待处理文件全路径名称
	 */
	private String localFullFileName;
	/**
	 * 处理后的未加密文件全路径
	 */
	private String resuDecryFullFileName;
	/**
	 * 处理后的加密文件全路径
	 */
	private String resuEncryFullFileName;

	/**
	 * 加密文件的秘钥
	 */
	private String encryKey;
	/**
	 * 结果文件的文件行数
	 */
	private int resuFileSize;

	public int getResuFileSize() {
		return resuFileSize;
	}

	public void setResuFileSize(int resuFileSize) {
		this.resuFileSize = resuFileSize;
	}

	public String getEncryKey() {
		return encryKey;
	}

	public void setEncryKey(String encryKey) {
		this.encryKey = encryKey;
	}

	public String getLocalFullFileName() {
		return localFullFileName;
	}

	public void setLocalFullFileName(String localFullFileName) {
		this.localFullFileName = localFullFileName;
	}

	public String getResuDecryFullFileName() {
		return resuDecryFullFileName;
	}

	public void setResuDecryFullFileName(String resuDecryFullFileName) {
		this.resuDecryFullFileName = resuDecryFullFileName;
	}

	public String getResuEncryFullFileName() {
		return resuEncryFullFileName;
	}

	public void setResuEncryFullFileName(String resuEncryFullFileName) {
		this.resuEncryFullFileName = resuEncryFullFileName;
	}

}
