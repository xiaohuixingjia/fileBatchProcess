package com.umpay.proxyservice;

import org.apache.mina.core.buffer.IoBuffer;
import org.apache.mina.filter.codec.http.MutableHttpRequest;
import org.apache.mina.filter.codec.http.MutableHttpResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.bs3.inf.IProcessors.HSessionInf;
import com.bs3.nio.mina2.Mina2H4Rpc2;
import com.bs3.nio.mina2.codec.IHttp;
import com.umpay.proxyservice.fileBatch.thread.TaskProcessThread;
import com.umpay.proxyservice.handler.TaskHandler;
import com.umpay.proxyservice.ruleTimeService.RuleTimeService;
import com.umpay.proxyservice.util.LogArgsUtil;
import com.umpay.proxyservice.util.SpringUtil;
import com.umpay.proxyservice.util.StringUtil;
import com.umpay.proxyservice.util.TimeCountUtil;

/**
 * 文件批处理请求入口
 * 
 * @author xuxiaojia
 *
 */
public class NioServerHandler extends Mina2H4Rpc2 {

	private final static Logger _log = LoggerFactory.getLogger("NioServerHandler");

	@Override
	protected void onServerReadReq(HSessionInf session, Object req) {
		// 设置当前请求线程的起始时间
		TimeCountUtil.setStartTime();
		MutableHttpRequest request = (MutableHttpRequest) req;
		String reqXML = getRequXml(request);
		String requestURL = request.getRequestUri().getPath();
		String ip = request.getHeader("X-Real-IP");
		if (StringUtil.isEmpty(ip))
			ip = "unknow";
		String responseStr = "";
		if (requestURL.equals("/fileBatch/fileTask")) {
			long startTime=System.currentTimeMillis();
			TaskHandler taskHandler = (TaskHandler) SpringUtil.getInstance().getContext().getBean("taskHandler");
			responseStr = taskHandler.execute(reqXML, ip);
			_log.info(LogArgsUtil.getLogArgus()+"处理请求耗时:"+(System.currentTimeMillis()-startTime)+"ms");
		} else if (requestURL.equals("/reload")) {
			_log.info("重载配置文件");
			if (RuleTimeService.getRts().reload()) {
				responseStr = "reloadSuccess";
			} else {
				responseStr = "reloadError";
			}
		} else if (requestURL.equals("/stopProcess")) {
			_log.info("停止处理任务");
			TaskProcessThread.isRun.set(false);
			responseStr = "stopSuccess";
		} else if (requestURL.equals("/startProcess")) {
			_log.info("开始处理任务");
			TaskProcessThread.isRun.set(true);
			responseStr = "startSuccess";
		} else {
			_log.info("url错误,请求的url为："+requestURL);
			responseStr = "urlError";
		}
		this.responseContent(session, responseStr);
	}

	/**
	 * 获取请求中的xml报文
	 * 
	 * @param req
	 * @return
	 */
	private String getRequXml(MutableHttpRequest request) {
		/* 接收请求，解析POST报文体 */
		IoBuffer content = (IoBuffer) request.getContent();
		byte[] conBytes = new byte[content.limit()];
		content.get(conBytes);
		String reqXML = new String(conBytes);
		return reqXML;
	}

	/**
	 * 返回响应给商户的方法
	 * 
	 * @param session
	 * @param responseStr
	 */
	private void responseContent(HSessionInf session, String responseStr) {
		try {
			/* 第四步：返回 */
			// _log.info("返回的报文如下:\n"+responseStr);
			MutableHttpResponse res = IHttp.makeResp(new IHttp.HResponse(), IHttp.HConst.SC_OK, "", null, "text/plain",
					responseStr.getBytes());
			session.write(res);
		} catch (Exception e) {
			_log.error("", e);
			session.close("");
		}
	}

}
