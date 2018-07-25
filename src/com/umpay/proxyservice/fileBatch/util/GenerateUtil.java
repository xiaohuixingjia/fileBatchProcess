package com.umpay.proxyservice.fileBatch.util;

import java.io.File;

import com.umpay.proxyservice.Constant;
import com.umpay.proxyservice.util.FileUtil;
import com.umpay.proxyservice.util.PropertiesUtil;
import com.umpay.proxyservice.util.TypeConversionUtil;

/**
 * 各种名称生成的常用工具类
 * 
 * @author xuxiaojia
 */
public class GenerateUtil {

	/**
	 * 由funcode和merid来生成key
	 * 
	 * @param funcode
	 * @param merid
	 * @return
	 */
	public static String generKeyByFuncodeMerid(String funcode, String merid) {
		return funcode + Constant.HENGXIAN_SEPARATOR + merid;
	}

	/**
	 * 根据funcode merid和dateTime来生成文件名
	 * 
	 * @param funcode
	 * @param merid
	 * @param dateTime
	 * @return
	 */
	public static String generFileName(String funcode, String merid, String dateTime) {
		return funcode + Constant.HENGXIAN_SEPARATOR + merid + Constant.HENGXIAN_SEPARATOR + dateTime + ".txt";
	}

	public static String getUploadPath(String taskId, String funcode, String merid) {
		String filePath= getPath(taskId,funcode,merid,"upload");
		FileUtil.mkdirs(filePath);
		return filePath;
	}

	private static String getPath(String taskId, String funcode, String merid,String uploadOrDownload){
		return PropertiesUtil.getInstance("config.properties").getConfigItem("fileBasePath") + funcode + File.separator
		+ merid + File.separator + uploadOrDownload + File.separator + taskId + File.separator;

	}
	
	public static String getDownloadPath(String taskId, String funcode, String merid) {
		String filePath= getPath(taskId,funcode,merid,"download");
		FileUtil.mkdirs(filePath);
		return filePath;
	}
	
	/**
	 * utf-8第一行信息去掉不可见字符
	 * @param lineInfo
	 * @return
	 */
	public static String utf8FirstStrChange(String lineInfo){
		try {
			String bytes2HexString = TypeConversionUtil.bytes2HexString(lineInfo.getBytes(Constant.UTF_8));
			if(bytes2HexString.startsWith("EFBBBF")){
				bytes2HexString=bytes2HexString.substring(6);
			}
			return new String(TypeConversionUtil.hexString2Bytes(bytes2HexString), Constant.UTF_8);
		} catch (Exception e) {
		}
		return null;
	}

}
