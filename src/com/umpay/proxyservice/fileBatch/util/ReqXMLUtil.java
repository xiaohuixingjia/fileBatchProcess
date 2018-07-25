package com.umpay.proxyservice.fileBatch.util;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;

import com.umpay.proxyservice.util.HttpMap;
import com.umpay.proxyservice.util.IdGeneratorUtil;
import com.umpay.proxyservice.util.MD5Utils;
import com.umpay.proxyservice.util.XmlUtils;

public class ReqXMLUtil {
	public static String getXMLStr(Map<String, String> map) {
		String datetime = new SimpleDateFormat("yyyyMMddHHmmss").format(new Date());
		String transid = "FBP" + IdGeneratorUtil.getIdGeneratorUtil().getId();
		if (StringUtils.isNotEmpty(map.get(HttpMap.TRANSID))) {
			transid = map.get(HttpMap.TRANSID);
		}

		String sign = MD5Utils.getMD5Str("funcode" + map.get("funcode") + "datetime" + datetime + "merid"
				+ map.get("merid") + "transid" + transid);
		map.put("sign", sign);
		map.put("datetime", datetime);
		map.put("transid", transid);
		return XmlUtils.mapToXml(map, "request");
	}

	public static String getNoticeXmlStr(Map<String, String> map) {
		List<String> list = new ArrayList<String>();
		list.add("datetime");
		list.add("dealcode");
		list.add("funcode");
		list.add("transid");
		Collections.sort(list);
		String datetime = new SimpleDateFormat("yyyyMMddHHmmss").format(new Date());
		map.put("datetime", datetime);
		StringBuffer oriSignStr = new StringBuffer();
		for (String string : list) {
			oriSignStr.append(string).append(map.get(string));
		}
		String sign = MD5Utils.getMD5Str(oriSignStr.toString());
		map.put("sign", sign);
		return XmlUtils.mapToXml(map, "request");
	}
}
