package com.umpay.proxyservice.fileBatch.tactics.writer.impl;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.UUID;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.atomic.AtomicInteger;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.umpay.proxyservice.Constant;
import com.umpay.proxyservice.exception.BaseException;
import com.umpay.proxyservice.fileBatch.bean.FileProcessTask;
import com.umpay.proxyservice.fileBatch.po.ConfigInfoPO;
import com.umpay.proxyservice.fileBatch.tactics.writer.InfoWriterAbstractTatics;
import com.umpay.proxyservice.fileBatch.thread.TpsLimitTask;
import com.umpay.proxyservice.fileBatch.util.GenerateUtil;
import com.umpay.proxyservice.fileBatch.util.ReqXMLUtil;
import com.umpay.proxyservice.fileBatch.util.ThreadUtil;
import com.umpay.proxyservice.limit.LimitCheckHandler;
import com.umpay.proxyservice.limit.bean.LimitInfo;
import com.umpay.proxyservice.ruleTimeService.RuleTimeService;
import com.umpay.proxyservice.util.AesEncryptUtil;
import com.umpay.proxyservice.util.HttpMap;
import com.umpay.proxyservice.util.WarnMsgUtil;

public class QueryDataWriter extends InfoWriterAbstractTatics<FileProcessTask> {
	protected final static Logger log = LoggerFactory.getLogger("QueryDataWriter");
	/* 请求的xml信息队列 */
	protected Queue<String> reqXmlQueue = new LinkedBlockingDeque<String>();
	/* 响应的xml信息队列 */
	protected Queue<String> respInfoQueue = new LinkedBlockingDeque<String>();
	/* 文件处理任务 */
	protected FileProcessTask fileProcessTask;
	/* 请求信息存放的集合 */
	protected List<String> reqInfo;
	/* 请求要素对应的下标 */
	protected Map<String, Integer> elemIndexMap;
	/* tps限制信息 */
	protected LimitInfo limitInfo;
	/* 可以进行流量控制的请求线程集合 */
	protected List<TpsLimitTask> list;
	/* 配置信息 */
	protected ConfigInfoPO configPo;
	/* 写出线程 */
	protected WriterThread writeResuThread;
	/* 当前文件读入的行数 */
	protected AtomicInteger currentLineNum;
	/* 查询数据的监控线程 */
	protected InnerMonitorThread monitorThread;
	/**
	 * 从队列中获取一个请求信息 队列为空时会返回null
	 * 
	 * @return
	 */
	public String pollOneQueryInfo() {
		return reqXmlQueue.poll();
	}

	/**
	 * 判断是否有请求报文
	 * 
	 * @return
	 */
	public boolean haveQueryInfo() {
		return reqXmlQueue.size() > 0;
	}

	/**
	 * 获取tps限制信息
	 * 
	 * @return
	 */
	public LimitInfo getLimitInfo() {
		return limitInfo;
	}

	/**
	 * 录入一个相应信息到队列
	 * 
	 * @param respXml
	 * @return
	 */
	public boolean offerNewRespXml(String respXml) {
		return respInfoQueue.offer(respXml);
	}

	/**
	 * 获取当先文件处理任务的id
	 * 
	 * @return
	 */
	public String getTaskId() {
		return this.fileProcessTask.getTaskId();
	}

	@Override
	public void init(FileProcessTask fileInfo) throws BaseException {
		initObjField(fileInfo);
		setInfo2task();
		initThread();
	}

	/**
	 * 初始化任务处理结果的文件信息
	 */
	protected void setInfo2task() {
		String filePath = GenerateUtil.getUploadPath(fileProcessTask.getTaskId(),
				fileProcessTask.getReqMap().get(HttpMap.FUNCODE), fileProcessTask.getReqMap().get(HttpMap.MERID));
		String fileName = fileProcessTask.getReqMap().get(HttpMap.FILE).split(Constant.POINT_SEPARATOR)[0];
		// 设置非加密结果文件全路径
		fileProcessTask.setResuDecryFullFileName(filePath + fileName + Constant.HENGXIAN_SEPARATOR
				+ fileProcessTask.getTaskId() + Constant.HENGXIAN_SEPARATOR + "decry.txt");
		// 设置加密结果文件全路径
		fileProcessTask.setResuEncryFullFileName(filePath + fileName + Constant.HENGXIAN_SEPARATOR
				+ fileProcessTask.getTaskId() + Constant.HENGXIAN_SEPARATOR + "encry.txt");
		// 设置上传的结果文件全路径
		fileProcessTask.setResuFullFileName(configPo.isNeedEncry() ? fileProcessTask.getResuEncryFullFileName()
				: fileProcessTask.getResuDecryFullFileName());
		// 设置加密秘钥
		fileProcessTask.setEncryKey(StringUtils.isEmpty(configPo.getEncryKey())
				? UUID.randomUUID().toString().substring(0, 16) : configPo.getEncryKey());
		log.info(fileProcessTask.getTaskId() + "当前文件加密的key为：" + fileProcessTask.getEncryKey());
	}

	/**
	 * 初始化当前writer对象的属性
	 * 
	 * @param fileInfo
	 * @throws BaseException
	 */
	protected void initObjField(FileProcessTask fileInfo) throws BaseException {
		fileProcessTask = fileInfo;
		reqInfo = new ArrayList<String>();
		elemIndexMap = new HashMap<String, Integer>();
		configPo = RuleTimeService.getRts().getConfigPo(fileProcessTask.getReqMap());
		limitInfo = new LimitInfo(fileProcessTask.getReqMap().get(HttpMap.FUNCODE),
				fileProcessTask.getReqMap().get(HttpMap.MERID), configPo.getMaxTps());
		currentLineNum=new AtomicInteger(0);
	}

	/**
	 * 初始化 请求和写出线程
	 */
	protected void initThread() {
		list = new ArrayList<TpsLimitTask>();
		LimitCheckHandler.getInstance().putTpsLimit2map(limitInfo);
		int threadNum = configPo.getQueryThreads();
		if (threadNum <= 0) {
			threadNum = (configPo.getMaxTps() / 5) + 1;
		}
		for (int n = 0; n < threadNum; n++) {
			TpsLimitTask tpsLimitTask = new TpsLimitTask(this, configPo);
			new Thread(tpsLimitTask).start();
			list.add(tpsLimitTask);
		}
		writeResuThread = new WriterThread(this);
		writeResuThread.start();
		monitorThread=new InnerMonitorThread(this);
		monitorThread.start();
	}

	@Override
	public void writeIn(String lineInfo, int lineNum) throws BaseException {
		currentLineNum.set(lineNum);
		if (lineNum == 1) {
			lineInfo = GenerateUtil.utf8FirstStrChange(lineInfo);
			initElemIndexMap(lineInfo);
		} else {
			String[] infos = lineInfo.split(Constant.LOG_SEPARATOR);
			// 检验各输入项
			List<String> fileMustHaveField = RuleTimeService.getRts().getConfigPo(fileProcessTask.getReqMap())
					.getFileMustHaveField();
			if (infos.length < fileMustHaveField.size()) {
				log.info(fileProcessTask.getTaskId() + "第" + lineNum + "行列数不匹配" + lineInfo);
				return;
			}
			for (String elem : fileMustHaveField) {
				if (!RuleTimeService.getRts().getPatternCheckService().elemValueCheck(infos[elemIndexMap.get(elem)],
						elem)) {
					log.info(fileProcessTask.getTaskId() + "第" + lineNum + "行" + elem + "元素值"
							+ infos[elemIndexMap.get(elem)] + "不符合规范");
					return;
				}
			}
			// 生成报文放入队列
			reqInfo.add(createReqMapXml(infos));
		}
		// 判断是否符合乱序规则
		if (reqInfo.size() > 500) {
			set2ReqQueue();
		}
	}

	/**
	 * 将list集合中的信息乱序后插入到请求队列中
	 */
	protected void set2ReqQueue() {
		// 达到五百个则乱序后加入队列
		Collections.shuffle(reqInfo);
		for (String string : reqInfo) {
			while (reqXmlQueue.size() >= 100000) {
				ThreadUtil.sleep(100);
			}
			reqXmlQueue.add(string);
		}
		reqInfo.clear();
	}

	/**
	 * 创建请求报文
	 * 
	 * @param infos
	 * @return
	 * @throws BaseException
	 */
	protected String createReqMapXml(String[] infos) throws BaseException {
		Map<String, String> xmlMap = new HashMap<String, String>();
		List<String> elemsFromQueryXml4QueryData = RuleTimeService.getRts().getConfigPo(fileProcessTask.getReqMap())
				.getElemsFromQueryXml4QueryData();
		// 从发起任务的请求报文中获取数据拼接到请求报文中
		for (String elemName : elemsFromQueryXml4QueryData) {
			xmlMap.put(elemName, fileProcessTask.getReqMap().get(elemName));
		}
		// 将文件中的元素拼接到请求报文中
		for (String elem : RuleTimeService.getRts().getConfigPo(fileProcessTask.getReqMap()).getFileMustHaveField()) {
			xmlMap.put(elem, infos[elemIndexMap.get(elem)]);
		}
		return ReqXMLUtil.getXMLStr(xmlMap);
	}

	protected void initElemIndexMap(String lineInfo) {
		String[] split = lineInfo.split(Constant.LOG_SEPARATOR);
		for (int i = 0; i < split.length; i++) {
			elemIndexMap.put(split[i], i);
		}
	}

	@Override
	public FileProcessTask end(boolean readEnd) throws BaseException {
		// 将剩余的list中的信息放入队列
		set2ReqQueue();
		// 将请求线程停止
		stopTpsLimitTask();
		// 将写文件的线程停止
		ThreadUtil.sleep(10000);
		stopWriterAndMonitorThred();
		// 如果是正常读取文件结束
		if (readEnd) {
			// 返回文件处理结果
			this.fileProcessTask.setResuFileSize(writeResuThread.lineNum);
		}
		return this.fileProcessTask;
	}

	protected void stopWriterAndMonitorThred() {
		while (respInfoQueue.size() > 0) {
			ThreadUtil.sleep(1000);
		}
		writeResuThread.setIsRun(false);
		monitorThread.setRun(false);
		ThreadUtil.sleep(1000);
	}

	protected void stopTpsLimitTask() {
		while (reqXmlQueue.size() > 0) {
			ThreadUtil.sleep(100);
		}
		// 任务运行结束
		for (TpsLimitTask tpsLimitTask : list) {
			tpsLimitTask.setRun(false);
		}
	}

	/**
	 * 监控数据写出策略中的队列信息
	* @author xuxiaojia
	 */
	class InnerMonitorThread extends Thread {
		private boolean isRun;
		private QueryDataWriter qu;

		public void setRun(boolean isRun) {
			this.isRun = isRun;
		}

		public InnerMonitorThread(QueryDataWriter qu) {
			super();
			this.qu = qu;
			this.isRun = true;
		}

		@Override
		public void run() {
			while (isRun) {
				StringBuilder builder=new StringBuilder(qu.getTaskId());
				builder.append("：任务的数据查询监控：当前文件共")
				.append(qu.fileProcessTask.getReqMap().get(HttpMap.SIZE))
				.append("条,已经读到第")
				.append(qu.currentLineNum.get())
				.append("条,待查询队列中还有")
				.append(qu.reqXmlQueue.size())
				.append("条未查询,查询结果队列中还有")
				.append(qu.respInfoQueue.size())
				.append("条未处理,共有")
				.append(qu.list.size())
				.append("个请求线程在请求数据");
				log.info(builder.toString());
				ThreadUtil.sleep(10000);
			}
		}
	}

	class WriterThread extends Thread {

		private QueryDataWriter qu;
		private boolean isRun;

		private int lineNum;

		public void setIsRun(boolean isRun) {
			this.isRun = isRun;
		}

		public WriterThread(QueryDataWriter qu) {
			this.qu = qu;
			isRun = true;
			lineNum = 0;
		}

		@Override
		public void run() {
			String filePath = GenerateUtil.getUploadPath(qu.getTaskId(),
					qu.fileProcessTask.getReqMap().get(HttpMap.FUNCODE),
					qu.fileProcessTask.getReqMap().get(HttpMap.MERID));
			String fileName = qu.fileProcessTask.getReqMap().get(HttpMap.FILE).split(Constant.POINT_SEPARATOR)[0];
			FileWriter decryFileWriter = null;
			FileWriter encryFileWriter = null;
			try {
				decryFileWriter = new FileWriter(filePath + fileName + Constant.HENGXIAN_SEPARATOR
						+ qu.getTaskId() + Constant.HENGXIAN_SEPARATOR + "decry.txt");
				encryFileWriter = new FileWriter(filePath + fileName + Constant.HENGXIAN_SEPARATOR
						+ qu.getTaskId() + Constant.HENGXIAN_SEPARATOR + "encry.txt");
				writeFirstLine(decryFileWriter, encryFileWriter);
				lineNum++;
				while (isRun) {
					String respStr = qu.respInfoQueue.poll();
					if (StringUtils.isEmpty(respStr)) {
						Thread.sleep(100);
						continue;
					}
					writeInfo(decryFileWriter, respStr, false, true);
					writeInfo(encryFileWriter, respStr, true, true);
					lineNum++;
				}
			} catch (Exception e) {
				WarnMsgUtil.sendEmail("批处理流程异常", qu.getTaskId() + ":在写出信息到文件的流程中出现异常，请查看");
				log.error(qu.getTaskId() + "：写出流程出现异常", e);
				// 打印队列中日志到本地
				while (isRun) {
					String respStr = qu.respInfoQueue.poll();
					if (StringUtils.isEmpty(respStr)) {
						continue;
					}
					log.info(qu.getTaskId() + "写出队列中的信息到日志:" + respStr);
				}
			} finally {
				closeWriter(decryFileWriter);
				closeWriter(encryFileWriter);
			}
		}

		/**
		 * 写第一行内容
		 * 
		 * @param decryFileWriter
		 * @param encyFileWriter
		 * @throws Exception
		 */
		private void writeFirstLine(FileWriter decryFileWriter, FileWriter encyFileWriter) throws Exception {
			StringBuffer buffer = new StringBuffer();
			for (String str : qu.configPo.getFileMustHaveField()) {
				buffer.append(str).append(Constant.LOG_SEPARATOR);
			}
			buffer.append("data");
			writeInfo(decryFileWriter, buffer.toString(), false, false);
			writeInfo(encyFileWriter, buffer.toString(), true, false);
		}

		private void writeInfo(FileWriter fileWriter, String string, boolean needEncry, boolean newLine)
				throws Exception {
			if (newLine) {
				fileWriter.write(Constant.NEW_LINE);
			}
			if (needEncry) {
				fileWriter.write(
						AesEncryptUtil.encrypt(string, qu.fileProcessTask.getEncryKey(), qu.configPo.getIvString()));
			} else {
				fileWriter.write(string);
			}
			fileWriter.flush();
		}

		private void closeWriter(FileWriter fileWriter) {
			if (fileWriter != null) {
				try {
					fileWriter.flush();
					fileWriter.close();
				} catch (IOException e) {
				}
			}
		}

	}
}
