package com.umpay.proxyservice.xmlTransfer.tactic.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;

import com.umpay.proxyservice.util.JaxbUtil;
import com.umpay.proxyservice.xmlTransfer.bean.Gup1006323RespBean;
import com.umpay.proxyservice.xmlTransfer.bean.base.Label;
import com.umpay.proxyservice.xmlTransfer.bean.base.LabelValue;
import com.umpay.proxyservice.xmlTransfer.tactic.TransferResponse;

/**
 * 银联智慧 cid评分定制转换 （最大分，求和分，平均分）
 * 
 * @author xuxiaojia
 */
public class Gup1006323_RespBeanTransfer implements TransferResponse {

	private static List<String> labelIds = new ArrayList<String>();
	private static List<String> yiciLabel = new ArrayList<String>();
	private static List<String> ms = new ArrayList<String>();

	static {
		yiciLabel.add("Pro00700");
		yiciLabel.add("Pro00702");
		yiciLabel.add("Pro00701");
		yiciLabel.add("Pro00704");
		yiciLabel.add("Pro00703");
		yiciLabel.add("Pro00705");
		yiciLabel.add("Pro00715");
		yiciLabel.add("Pro00712");
		yiciLabel.add("Pro00707");
		yiciLabel.add("Pro00706");
		yiciLabel.add("Pro00709");
		yiciLabel.add("Pro00708");
		yiciLabel.add("Pro00711");
		yiciLabel.add("Pro00710");
		yiciLabel.add("Pro00713");
		yiciLabel.add("Pro00725");
		yiciLabel.add("Pro00723");
		yiciLabel.add("Pro00717");
		yiciLabel.add("Pro00718");
		yiciLabel.add("Pro00722");
		yiciLabel.add("Pro00720");
		yiciLabel.add("Pro00716");
		yiciLabel.add("Pro00714");
		yiciLabel.add("Pro00611");
		yiciLabel.add("Pro00612");
		yiciLabel.add("Pro00613");
		yiciLabel.add("Pro00614");
		yiciLabel.add("Pro00615");
		yiciLabel.add("Pro00616");
		yiciLabel.add("Pro00617");
		yiciLabel.add("Pro00618");
		yiciLabel.add("Pro00619");
		yiciLabel.add("Pro00621");
		yiciLabel.add("Pro00620");
		labelIds.add("Pro00003");
		labelIds.add("Pro00002");
		labelIds.add("Pro00011");
		labelIds.add("Pro00010");
		labelIds.add("Pro00008");
		labelIds.add("Pro00007");
		labelIds.add("Pro00005");
		labelIds.add("Pro00004");
		labelIds.add("Pro00095");
		labelIds.add("Pro00094");
		labelIds.add("Pro00100");
		labelIds.add("Pro00099");
		labelIds.add("Pro00097");
		labelIds.add("Pro00193");
		labelIds.add("Pro00194");
		labelIds.add("Pro00197");
		labelIds.add("Pro00198");
		labelIds.add("Pro00199");
		labelIds.add("Pro00200");
		labelIds.add("Pro00213");
		labelIds.add("Pro00214");
		labelIds.add("Pro00043");
		labelIds.add("Pro00042");
		labelIds.add("Pro00041");
		labelIds.add("Pro00040");
		labelIds.add("Pro00047");
		labelIds.add("Pro00046");
		labelIds.add("Pro00045");
		labelIds.add("Pro00044");
		labelIds.add("Pro00051");
		labelIds.add("Pro00050");
		labelIds.add("Pro00049");
		labelIds.add("Pro00048");
		labelIds.add("Pro00215");
		labelIds.add("Pro00216");
		labelIds.add("Pro00217");
		labelIds.add("Pro00218");
		labelIds.add("Pro00219");
		labelIds.add("Pro00220");
		labelIds.add("Pro00088");
		labelIds.add("Pro00086");
		labelIds.add("Pro00085");
		labelIds.add("Pro00091");
		labelIds.add("Pro00090");
		labelIds.add("Pro00111");
		labelIds.add("Pro00202");
		labelIds.add("Pro00201");
		labelIds.add("Pro00195");
		labelIds.add("Pro00196");
		labelIds.add("Pro00205");
		labelIds.add("Pro00206");
		labelIds.add("Pro00207");
		labelIds.add("Pro00208");
		labelIds.add("Pro00211");
		labelIds.add("Pro00212");
		labelIds.add("Pro00167");
		labelIds.add("Pro00168");
		labelIds.add("Pro00169");
		labelIds.add("Pro00170");
		labelIds.add("Pro00171");
		labelIds.add("Pro00172");
		labelIds.add("Pro00173");
		labelIds.add("Pro00174");
		labelIds.add("Pro00175");
		labelIds.add("Pro00176");
		labelIds.add("Pro00177");
		labelIds.add("Pro00178");
		labelIds.add("Pro00221");
		labelIds.add("Pro00222");
		labelIds.add("Pro00225");
		labelIds.add("Pro00226");
		labelIds.add("Pro00191");
		labelIds.add("Pro00227");
		labelIds.add("Pro00001");
		labelIds.add("Pro00009");
		labelIds.add("Pro00006");
		labelIds.add("Pro00096");
		labelIds.add("Pro00093");
		labelIds.add("Pro00098");
		labelIds.add("Pro00228");
		labelIds.add("Pro00229");
		labelIds.add("Pro00230");
		labelIds.add("Pro00232");
		labelIds.add("Pro00233");
		labelIds.add("Pro00234");
		labelIds.add("Pro00235");
		labelIds.add("Pro00087");
		labelIds.add("Pro00089");
		labelIds.add("Pro00236");
		labelIds.add("Pro00237");
		labelIds.add("Pro00239");
		labelIds.add("Pro00240");
		labelIds.add("Pro00242");
		labelIds.add("Pro00243");
		labelIds.add("Pro00270");
		labelIds.add("Pro00244");
		labelIds.add("Pro00109");
		labelIds.add("Pro00115");
		labelIds.add("Pro00116");
		labelIds.add("Pro00114");
		labelIds.add("Pro00103");
		labelIds.add("Pro00101");
		labelIds.add("Pro00055");
		labelIds.add("Pro00054");
		labelIds.add("Pro00053");
		labelIds.add("Pro00052");
		labelIds.add("Pro00012");
		labelIds.add("Pro00013");
		labelIds.add("Pro00014");
		labelIds.add("Pro00015");
		labelIds.add("Pro00016");
		labelIds.add("Pro00017");
		labelIds.add("Pro00018");
		labelIds.add("Pro00019");
		labelIds.add("Pro00253");
		labelIds.add("Pro00254");
		labelIds.add("Pro00068");
		labelIds.add("Pro00070");
		labelIds.add("Pro00069");
		labelIds.add("Pro00056");
		labelIds.add("Pro00179");
		labelIds.add("Pro00180");
		labelIds.add("Pro00181");
		labelIds.add("Pro00182");
		labelIds.add("Pro00183");
		labelIds.add("Pro00184");
		labelIds.add("Pro00185");
		labelIds.add("Pro00186");
		labelIds.add("Pro00187");
		labelIds.add("Pro00188");
		labelIds.add("Pro00189");
		labelIds.add("Pro00190");
		labelIds.add("Pro00192");
		labelIds.add("Pro00286");
		labelIds.add("Pro00110");
		labelIds.add("Pro00113");
		labelIds.add("Pro00104");
		labelIds.add("Pro00292");
		labelIds.add("Pro00067");
		labelIds.add("Pro00057");
		labelIds.add("Pro00304");
		labelIds.add("Pro00059");
		labelIds.add("Pro00062");
		labelIds.add("Pro00061");
		labelIds.add("Pro00079");
		labelIds.add("Pro00700");
		labelIds.add("Pro00702");
		labelIds.add("Pro00701");
		labelIds.add("Pro00704");
		labelIds.add("Pro00703");
		labelIds.add("Pro00705");
		labelIds.add("Pro00715");
		labelIds.add("Pro00712");
		labelIds.add("Pro00707");
		labelIds.add("Pro00706");
		labelIds.add("Pro00709");
		labelIds.add("Pro00708");
		labelIds.add("Pro00711");
		labelIds.add("Pro00710");
		labelIds.add("Pro00713");
		labelIds.add("Pro00725");
		labelIds.add("Pro00723");
		labelIds.add("Pro00717");
		labelIds.add("Pro00718");
		labelIds.add("Pro00722");
		labelIds.add("Pro00720");
		labelIds.add("Pro00716");
		labelIds.add("Pro00714");
		labelIds.add("Pro00600");
		labelIds.add("Pro00601");
		labelIds.add("Pro00602");
		labelIds.add("Pro00603");
		labelIds.add("Pro00604");
		labelIds.add("Pro00605");
		labelIds.add("Pro00611");
		labelIds.add("Pro00612");
		labelIds.add("Pro00613");
		labelIds.add("Pro00614");
		labelIds.add("Pro00615");
		labelIds.add("Pro00616");
		labelIds.add("Pro00606");
		labelIds.add("Pro00607");
		labelIds.add("Pro00608");
		labelIds.add("Pro00610");
		labelIds.add("Pro00609");
		labelIds.add("Pro00617");
		labelIds.add("Pro00618");
		labelIds.add("Pro00619");
		labelIds.add("Pro00621");
		labelIds.add("Pro00620");
		ms.add("1");
		ms.add("2");
		ms.add("3");
		ms.add("4");
		ms.add("5");
		ms.add("6");
		ms.add("7");
		ms.add("8");
		ms.add("9");
		ms.add("10");
		ms.add("11");
		ms.add("12");
	}

	@Override
	public String transfer(String respXml) throws Exception {
		StringBuilder resu = new StringBuilder();
		try {
			Gup1006323RespBean converyToJavaBean = JaxbUtil.converyToJavaBean(respXml, Gup1006323RespBean.class);
			if (converyToJavaBean.getRetcode().equals("0000")) {
				List<Label> labels = converyToJavaBean.getLabel();
				// labelId_m value
				Map<String, String> map = new HashMap<String, String>();
				for (Label label : labels) {
					List<LabelValue> vs = label.getValue();
					for (LabelValue labelValue : vs) {
						map.put(label.getId()+"_"+labelValue.getM(), labelValue.getValue());
					}
				}
				for (String m : ms) {
					for (String labelid : labelIds) {
						String v=map.get(labelid+"_"+m);
						if(StringUtils.isEmpty(v)){
							v="";
						}
						if(yiciLabel.contains(labelid)){
							v="*";
						}
						resu.append(v).append("\t");
					}
					resu.append("%%&&");
				}
			}
		} catch (Exception e) {
			throw e;
		}
		return resu.toString();
	}

	public static void main(String[] args) {
//		String str = "<request>	<funcode>Gup1006323</funcode>	<datetime>20171026091053</datetime>	<merid>10001001</merid>	<transid>201710260910532330000083130</transid>	<name>张小凡</name>	<querymonth>201711</querymonth>	<mobileid>13692368460</mobileid>	<license>0ahk07rbujmd5ybvpzfp</license>	<identityNo>140202199404323219</identityNo>	<sign>80825ae4c8b624f4aa4cb6ff9769047e</sign></request>";
//		String post = HttpUtil.post("http://10.102.5.53:9005/umpaydc/dataQuery/", str);
//		System.out.println(post);
//		Gup1006323RespBean converyToJavaBean = JaxbUtil.converyToJavaBean(post, Gup1006323RespBean.class);
//		System.out.println(converyToJavaBean.toString());
		String a="201711";
		String x=a.substring(4);
		Integer mm=Integer.parseInt(x);
		Integer yyyy=Integer.parseInt(a.substring(0, 4));
		int num=0;
		while(num++<12){
			if(mm==1){
				mm=12;
				yyyy-=1;
			}
			if(mm<=10){
				System.out.println(yyyy+"0"+(--mm));
			}else{
				System.out.println(yyyy+""+(--mm));
			}
		}
		
	}
}
