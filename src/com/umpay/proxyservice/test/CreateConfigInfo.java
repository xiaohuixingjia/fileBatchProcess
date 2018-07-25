package com.umpay.proxyservice.test;

import java.io.File;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.List;

import com.umpay.proxyservice.anno.exception.AnnoException;
import com.umpay.proxyservice.anno.util.AnnoCheckUtil;
import com.umpay.proxyservice.fileBatch.po.ConfigInfoPO;
import com.umpay.proxyservice.util.HttpMap;
import com.umpay.proxyservice.util.JacksonUtil;

public class CreateConfigInfo {
	public static void main(String[] args) throws Exception {
		main_forTest(args);
	}
	
	public static ConfigInfoPO.ConfigInfoBuilder getCommonBuilder(){
		return CreateCommonConfigInfoBuilder.localPorcess();
//		return CreateCommonConfigInfoBuilder.queryCheckPorcess();
	}
	
	
	public static void main_forTest(String[] args) throws Exception {
		List<ConfigInfoPO> infos=new ArrayList<ConfigInfoPO>();
		infos.add(prod_generXIGUA_tanxing());
		FileWriter writer=new FileWriter(new File(System.getProperty("user.dir")+"/resource/"+"ConfigInfoPO.txt"));
		writer.write(JacksonUtil.obj2json(infos));
		writer.flush();
		writer.close();
//		testJson();
	}
	private static ConfigInfoPO prod_generXIGUA_tanxing() throws AnnoException {
		List<String> reqFieldHaveList=new ArrayList<String>();
		reqFieldHaveList.add(HttpMap.FUNCODE);
		reqFieldHaveList.add(HttpMap.TRANSID);
		reqFieldHaveList.add(HttpMap.DATETIME);
		reqFieldHaveList.add(HttpMap.LICENSE);
		reqFieldHaveList.add(HttpMap.MERID);
		reqFieldHaveList.add(HttpMap.SIZE);
		reqFieldHaveList.add(HttpMap.FILE);
		reqFieldHaveList.add(HttpMap.SIGN);
		reqFieldHaveList.add(HttpMap.NAME);
		reqFieldHaveList.add(HttpMap.IDENTITYNO);
		List<String> fileMustHaveField=new ArrayList<String>();
		fileMustHaveField.add(HttpMap.MOBILEID);
		List<String> elemsFromQueryXml4QueryData=new ArrayList<String>();
		elemsFromQueryXml4QueryData.add(HttpMap.FUNCODE);
		elemsFromQueryXml4QueryData.add(HttpMap.MERID);
		elemsFromQueryXml4QueryData.add(HttpMap.LICENSE);
		elemsFromQueryXml4QueryData.add(HttpMap.NAME);
		elemsFromQueryXml4QueryData.add(HttpMap.IDENTITYNO);
		ConfigInfoPO info = getCommonBuilder()
				.setFuncode("")
				.setMerid("")
				.setLicense("")
				.setReqFieldMustHaveList(reqFieldHaveList)
				.setElemsFromQueryXml4QueryData(elemsFromQueryXml4QueryData)
				.setFileMustHaveField(fileMustHaveField)
				.setTransferResponseClass("com.umpay.proxyservice.xmlTransfer.tactic.impl.XiGuaFenTransfer")
				.build();
		AnnoCheckUtil.getAnnoCheckUtil().validateAllAnno(info,info.getProcessType());
		return info;
	}

	

}
