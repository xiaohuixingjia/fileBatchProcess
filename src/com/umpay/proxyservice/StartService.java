package com.umpay.proxyservice;

import java.net.URISyntaxException;

import com.bs3.ioc.core.BeansContext;
import com.bs3.utils.MyLog;
import com.umpay.proxyservice.util.SpringUtil;

/**
 * 启动服务
 * 
 * @author xuxiaojia
 * @date 2016年6月15日 上午10:18:15
 */
public class StartService {
	private static final MyLog log = MyLog.getLog(StartService.class);

	public static void main(String[] args) throws URISyntaxException, Exception {
		try {
			log.info("文件批处理服务开始启动");
			BeansContext ctx = BeansContext.getInstance();
			SpringUtil.getInstance().getContext();
			ctx.setMappingFile("resource/mina2niocs.properties");
			ctx.start();
			log.info("文件批处理服务启动成功");
		} catch (Exception e) {
			log.error(e, "文件批处理服务启动失败");
		}
 
	}

}
