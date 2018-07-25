package com.umpay.proxyservice.util;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

public class HttpUtil {
	private final static String REQUEST_METHOD = "POST";
	private final static String REQUEST_CONTENT_TYPE = "Content-type";
	private final static String REQUEST_CONTENT_TYPE_VALUE = "text/plain";
	private final static String REQUEST_CHARSET = "Accept-Charset";
	private final static String REQUEST_CHARSET_VALUE = "UTF-8";
	private final static String REQUEST_CONTENT_LENGTH = "Content-Length";
	/* post请求默认读取超时时间 */
	private final static int READ_TIME_OUT = 1 * 1000;
	/* post请求默认连接超时时间 */
	private final static int CONNECT_TIME_OUT = 1 * 1000;

	/**
	 * 发送post请求
	 * 
	 * @param url
	 *            连接的url
	 * @param str
	 *            发送的数据
	 * @return 返回的报文
	 */
	public static String post(String url, String str) {
		return HttpUtil.post(url, str, READ_TIME_OUT, CONNECT_TIME_OUT);
	}

	/**
	 * 发送含有读取时时间和连接时间的post请求
	 * 
	 * @param u
	 *            连接的url
	 * @param str
	 *            发送的数据
	 * @param readTimeOut
	 *            读取超时时间
	 * @param connectTimeOut
	 *            连接超时时间
	 * @return 返回的请求报文
	 */
	public static String post(String u, String str, int readTimeOut, int connectTimeOut) {
		StringBuffer sb = new StringBuffer();
		HttpURLConnection huc = null;
		DataOutputStream printout = null;
		InputStream is = null;
		BufferedReader br = null;
		try {
			URL url = new URL(u);
			huc = (HttpURLConnection) url.openConnection();
			huc.setRequestMethod(REQUEST_METHOD);
			huc.setRequestProperty(REQUEST_CONTENT_TYPE, REQUEST_CONTENT_TYPE_VALUE);
			huc.setRequestProperty(REQUEST_CHARSET, REQUEST_CHARSET_VALUE);
			huc.setRequestProperty(REQUEST_CONTENT_LENGTH, String.valueOf(str.getBytes().length));
			huc.setDoOutput(true);
			huc.setReadTimeout(readTimeOut);
			huc.setConnectTimeout(connectTimeOut);
			printout = new DataOutputStream(huc.getOutputStream());
			printout.write(str.getBytes());
			printout.flush();
			String line;
			is = huc.getInputStream();
			br = new BufferedReader(new InputStreamReader(is));
			while ((line = br.readLine()) != null) {
				sb.append(line).append("\n");
			}
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
//			if (printout != null)
//				try {
//					printout.close();
//					if (br != null)
//						br.close();
//					if (is != null)
//						is.close();
//					if (huc != null)
//						huc.disconnect();
//				} catch (IOException e) {
//				}
		}
		return sb.toString();
	}

	/**
	 * 发送含有读取时时间和连接时间的get请求
	 * 
	 * @param url
	 *            连接的url
	 * @return 返回的请求报文
	 */
	public static String sendGet(String url) {
		URLConnection connection = getURLConnection(url);
		return sendGet(connection);
	}

	/**
	 * 发送get请求
	 * 
	 * @param url
	 *            连接的url
	 * @param readTimeOut
	 *            读取超时时间
	 * @param connectTimeOut
	 *            连接超时时间
	 * @return 返回的请求报文
	 */
	public static String sendGet(String url, int readTimeOut, int connectTimeOut) {
		URLConnection connection = getURLConnection(url);
		connection.setReadTimeout(readTimeOut);
		connection.setConnectTimeout(connectTimeOut);
		return sendGet(connection);
	}

	/**
	 * 获取get发送的url配置实例
	 * 
	 * @param url
	 *            连接的url
	 * @return url配置实例
	 */
	private static URLConnection getURLConnection(String url) {
		URLConnection connection = null;
		try {
			URL realUrl = new URL(url);
			connection = realUrl.openConnection();
			System.out.println(connection.getHeaderFields());
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return connection;
	}

	/**
	 * 根据url配置实例发送get请求
	 * 
	 * @param connection
	 *            url配置实例
	 * @return 返回的请求报文
	 */
	private static String sendGet(URLConnection connection) {
		String result = "";
		BufferedReader in = null;
		try {
			in = new BufferedReader(new InputStreamReader(connection.getInputStream(), "utf-8"));
			String line;
			while ((line = in.readLine()) != null) {
				result += line;
			}
		} catch (UnsupportedEncodingException e) {
		} catch (IOException e) {
		} finally {
			try {
				if (in != null)
					in.close();
			} catch (Exception e2) {
			}
		}
		return result;
	}

	public static void main(String[] args) {
//		while (true) {
			try {
				//&&
//				System.out.println(HttpUtil.post("http://10.102.5.51:9009/umpaydc/dataQuery?queryType=2", "<?xml version=\"1.0\" encoding=\"UTF-8\"?><request>  <agreeMent>P0000140</agreeMent>  <childmerid>unknow</childmerid>  <datetime>20180703001002</datetime>  <funcode>Gck00012</funcode>  <identityNo>44018220000323061X</identityNo>  <isFound>0</isFound>  <isbatch>0</isbatch>  <license>hus2rn7guwh08t7gr4zx</license>  <merid>baidu</merid>  <mobileid>17666501323</mobileid>  <name>陈裕辉</name>  <operatorType>2</operatorType>  <orderId>unknow</orderId>  <queryTimes>1</queryTimes>  <readTime>10000</readTime>  <runNumber>1</runNumber>  <sequence>15305478020613698</sequence>  <sign>a4113335d7ca61f8958e516ca624b17d</sign>  <transid>0602000621</transid></request>"));
				System.out.println(HttpUtil.post("http://localhost:9018/umpaydc/dataQuery","{\"identityNo\":\"530322199502162421\",\"license\":\"m3gpvt97zhxjm94fez9b\",\"datetime\":\"20180716153005\",\"funcode\":\"Gup1006375\",\"isFromBox\":\"true\",\"mobileid\":\"13529236512\",\"transid\":\"X163676936441208832WC55\",\"sign\":\"00d69153a95dea3514f53c4a1369d28b\",\"name\":\"王艳\",\"merid\":\"03801001\",\"childmerid\":\"kuaiqian%\"}"));
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
//	}
}