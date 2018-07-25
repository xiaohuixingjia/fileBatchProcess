package com.umpay.proxyservice.test;

import java.io.File;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.List;

import com.umpay.proxyservice.anno.exception.AnnoException;
import com.umpay.proxyservice.anno.util.AnnoCheckUtil;
import com.umpay.proxyservice.fileBatch.po.ConfigInfoPO;
import com.umpay.proxyservice.handler.ProcessExecuteHandler.ProcessType;
import com.umpay.proxyservice.util.HttpMap;
import com.umpay.proxyservice.util.JacksonUtil;

public class CreateConfigInfo4Prod {
	public static void main(String[] args) throws Exception {
		main_forProd(args);
	}
	public static void main_forProd(String[] args) throws Exception {
		List<ConfigInfoPO> infos=new ArrayList<ConfigInfoPO>();
		infos.add(Prod_generYLZH_cid_score_10001001());	
		FileWriter writer=new FileWriter(new File(System.getProperty("user.dir")+"/resource/"+"ConfigInfoPO.txt"));
		writer.write(JacksonUtil.obj2json(infos));
		writer.flush();
		writer.close();
	}
	private static ConfigInfoPO Prod_generYLZH_cid_score_10001001() throws AnnoException {
		List<String> reqFieldHaveList=new ArrayList<String>();
		reqFieldHaveList.add(HttpMap.FUNCODE);
		reqFieldHaveList.add(HttpMap.TRANSID);
		reqFieldHaveList.add(HttpMap.DATETIME);
		reqFieldHaveList.add(HttpMap.LICENSE);
		reqFieldHaveList.add(HttpMap.MERID);
		reqFieldHaveList.add(HttpMap.SIZE);
		reqFieldHaveList.add(HttpMap.FILE);
		reqFieldHaveList.add(HttpMap.SIGN);
		reqFieldHaveList.add(HttpMap.LABEL_TYPE);
		reqFieldHaveList.add(HttpMap.CHILDMERID);
		List<String> reqSignCheckList1=new ArrayList<String>();
		reqSignCheckList1.add(HttpMap.FUNCODE);
		reqSignCheckList1.add(HttpMap.TRANSID);
		reqSignCheckList1.add(HttpMap.DATETIME);
		reqSignCheckList1.add(HttpMap.MERID);
		reqSignCheckList1.add(HttpMap.LICENSE);
		reqSignCheckList1.add(HttpMap.CHILDMERID);
		List<String> respSignCheckList1=new ArrayList<String>();
		respSignCheckList1.add(HttpMap.FUNCODE);
		respSignCheckList1.add(HttpMap.RETCODE);
		respSignCheckList1.add(HttpMap.DATETIME);
		List<String> reqSignCreateList1=new ArrayList<String>();
		reqSignCreateList1.add(HttpMap.DEALCODE);
		reqSignCreateList1.add(HttpMap.FUNCODE);
		reqSignCreateList1.add(HttpMap.TRANSID);
		reqSignCreateList1.add(HttpMap.DATETIME);
		List<String> respSignCreateList1=new ArrayList<String>();
		respSignCreateList1.add(HttpMap.RETCODE);
		respSignCreateList1.add(HttpMap.DATETIME);
		List<String> fileMustHaveField=new ArrayList<String>();
		fileMustHaveField.add(HttpMap.MOBILEID);
		List<String> empowerIps=new ArrayList<String>();
		empowerIps.add("127.0.0.1");
		empowerIps.add("localhost");
		List<String> elemsFromQueryXml4QueryData=new ArrayList<String>();
		elemsFromQueryXml4QueryData.add(HttpMap.FUNCODE);
		elemsFromQueryXml4QueryData.add(HttpMap.MERID);
		elemsFromQueryXml4QueryData.add(HttpMap.LICENSE);
		elemsFromQueryXml4QueryData.add(HttpMap.LABEL_TYPE);
		elemsFromQueryXml4QueryData.add(HttpMap.CHILDMERID);
		ConfigInfoPO info = new ConfigInfoPO.ConfigInfoBuilder()
				.setQueryDataUrl("")
				.setFuncode("")
				.setMerid("")
				.setChildmerid("")
				.setLicense("")
				.setFtpIp("")
				.setFtpPort(11234)
				.setFtpUser("")
				.setFtpPwd("")
				.setFtpUploadPath("")
				.setFtpDownloadPath("")
				.setFileMaxSize(5000000)
				.setFileMinSize(100)
				.setNeedEncry(true)
				.setEncryKey("")
				.setIvString("")
				.setNoticeUrl("")
				.setReqFieldMustHaveList(reqFieldHaveList)
				.setReqSignCheckList(reqSignCheckList1)
				.setRespSignCheckList(respSignCheckList1)
				.setReqSignCreateList(reqSignCreateList1)
				.setRespSignCreateList(respSignCreateList1)
				.setFileMustHaveField(fileMustHaveField)
				.setElemsFromQueryXml4QueryData(elemsFromQueryXml4QueryData)
				.setEmpowerIps(empowerIps)
				.setMaxTps(20)
				.setTransferResponseClass("com.umpay.proxyservice.xmlTransfer.tactic.impl.YLZH_CID_RespBeanTransfer")
				.setProcessType(ProcessType.localProcess)
				.build();
		AnnoCheckUtil.getAnnoCheckUtil().validateAllAnno(info,info.getProcessType());
		return info;
	}
	
	
}
