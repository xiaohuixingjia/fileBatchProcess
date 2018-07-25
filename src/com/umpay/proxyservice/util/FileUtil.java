package com.umpay.proxyservice.util;

import java.io.File;

/**
 * 文件操作常用类
 * 
 * @author xuxiaojia
 */
public class FileUtil {

	/**
	 * 创建文件夹
	 * 
	 * @param path
	 * @return
	 */
	public static boolean mkdirs(String path) {
		File file = new File(path);
		if(file.exists()){
			return true;
		}
		return file.mkdirs();
	}

	/**
	 * 传入文件的全路径判断文件是否存在
	 * 
	 * @param fullPath
	 * @return
	 */
	public static boolean fileExist(String fullPath) {
		File file = new File(fullPath);
		return file.exists();
	}

	/**
	 * 文件不存在
	 * 
	 * @param oriFileFullPath
	 * @return
	 */
	public static boolean fileNotExist(String fullPath) {
		return !fileExist(fullPath);
	}
	
}
