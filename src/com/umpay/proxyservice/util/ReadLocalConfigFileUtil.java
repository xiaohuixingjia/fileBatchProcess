package com.umpay.proxyservice.util;

import java.io.File;
import java.io.FileInputStream;
import java.util.List;
import java.util.Scanner;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.umpay.proxyservice.Constant;
import com.umpay.proxyservice.constant.Retcode;
import com.umpay.proxyservice.exception.BaseException;
import com.umpay.proxyservice.util.JacksonUtil;

public class ReadLocalConfigFileUtil {
	private static final Logger log = LoggerFactory.getLogger(ReadLocalConfigFileUtil.class);

	private static final String PFILE = System.getProperty("user.dir")
			+ "/resource/";// 路径

	public <T> List<T> getConfig(Class<T> t) throws Exception {
		log.info("获取" + t.getSimpleName() + "对应文件的路径是："+PFILE+t.getSimpleName()+".txt");
		
		File file = new File(PFILE+t.getSimpleName()+".txt");
		if (!file.exists()) {
			throw new BaseException(Retcode.CONFIG_FILE_NOT_FOUND,"未找到" + t.getSimpleName() + "的配置文件");
		}
		StringBuffer buffer = new StringBuffer();
		Scanner sc = null;
		try {
			sc = new Scanner(new FileInputStream(file), Constant.UTF_8);
			// 逐行读取解析
			while (sc.hasNextLine()) {
				buffer.append(sc.nextLine());
			}
			log.info("文件信息读取完毕" + buffer.toString());
			
			return  JacksonUtil.jacksonToCollection(buffer.toString(),List.class,t );
		} catch (Exception e) {
			throw e;
		} finally {
			if (sc != null) {
				try {
					sc.close();
				} catch (Exception e) {
				}
			}
		}
	}


}
