package com.umpay.proxyservice.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;

import org.apache.commons.lang.StringUtils;
import org.apache.http.Consts;
import org.apache.http.Header;
import org.apache.http.HeaderElement;
import org.apache.http.HeaderElementIterator;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpEntityEnclosingRequestBase;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.config.Registry;
import org.apache.http.config.RegistryBuilder;
import org.apache.http.conn.ConnectionKeepAliveStrategy;
import org.apache.http.conn.socket.ConnectionSocketFactory;
import org.apache.http.conn.socket.PlainConnectionSocketFactory;
import org.apache.http.conn.ssl.AllowAllHostnameVerifier;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.conn.ssl.SSLContextBuilder;
import org.apache.http.conn.ssl.SSLSocketFactory;
import org.apache.http.conn.ssl.TrustSelfSignedStrategy;
import org.apache.http.conn.ssl.TrustStrategy;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.message.BasicHeader;
import org.apache.http.message.BasicHeaderElementIterator;
import org.apache.http.protocol.HTTP;
import org.apache.http.protocol.HttpContext;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * http post
 * 
 * @author
 * @date 2016年7月27日
 */
@SuppressWarnings({ "deprecation", "unused" })
public class HttpClientUtil {
	private final static Logger log = LoggerFactory.getLogger("HttpClientUtil");

	static {
		HttpsURLConnection.setDefaultHostnameVerifier(new AllowAllHostnameVerifier());
		SSLSocketFactory.getSocketFactory().setHostnameVerifier(new AllowAllHostnameVerifier());
	}
	public static CloseableHttpClient httpclient;

	// 获得池化得HttpClient
	static {
		ConnectionKeepAliveStrategy myStrategy = new ConnectionKeepAliveStrategy() {
			@Override
			public long getKeepAliveDuration(HttpResponse response, HttpContext context) {
				HeaderElementIterator it = new BasicHeaderElementIterator(
						response.headerIterator(HTTP.CONN_KEEP_ALIVE));
				while (it.hasNext()) {
					HeaderElement he = it.nextElement();
					String param = he.getName();
					String value = he.getValue();
					if (value != null && param.equalsIgnoreCase("timeout")) {
						return Long.parseLong(value) * 1000;
					}
				}
				return 60 * 1000;// 如果没有约定，则默认定义时长为60s
			}
		};
		SSLContext sslcontext = null;
		try {
			sslcontext = new SSLContextBuilder().loadTrustMaterial(null, new TrustStrategy() {
				// 默认信任所有证书
				public boolean isTrusted(X509Certificate[] arg0, String arg1) throws CertificateException {
					return true;
				}
			}).build();

		} catch (Exception e) {
			log.error("初始化https连接池失败：", e);
		}

		SSLConnectionSocketFactory sslcsf = new SSLConnectionSocketFactory(sslcontext,
				SSLConnectionSocketFactory.ALLOW_ALL_HOSTNAME_VERIFIER);
		HostnameVerifier hostnameVerifier = SSLConnectionSocketFactory.ALLOW_ALL_HOSTNAME_VERIFIER;
		SSLConnectionSocketFactory sslsf = new SSLConnectionSocketFactory(sslcontext, hostnameVerifier);
		Registry<ConnectionSocketFactory> socketFactoryRegistry = RegistryBuilder.<ConnectionSocketFactory> create()
				.register("http", PlainConnectionSocketFactory.getSocketFactory()).register("https", sslsf).build();
		PoolingHttpClientConnectionManager connectionManager = new PoolingHttpClientConnectionManager(
				socketFactoryRegistry);
		connectionManager.setMaxTotal(500);
		connectionManager.setDefaultMaxPerRoute(50);
		httpclient = HttpClients.custom().setConnectionManager(connectionManager).build();
	}
	
	private HttpClientUtil() {
	}

	/**
	 * 默认的请求超时时间，3秒
	 */
	public final static int DEFAULT_REQUEST_TIMEOUT = 60 * 1000;
	/**
	 * 默认的响应超时时间，60秒
	 */
	public final static int DEFAULT_CONNECT_TIMEOUT = 3 * 1000;

	/**
	 * 
	 * @param url
	 * @param content
	 * @param contentType
	 * @return
	 * @throws Exception
	 */
	public static String sendByPost(String url, String content, String contentType) throws Exception {
		log.info("发送的路径："+url+"发送的报文："+content.replace("\r\n", "").replace("\n", ""));
		StringEntity resEntity = new StringEntity(content, Consts.UTF_8);
		List<Header> headers = new ArrayList<Header>();
		Header header = null;
		if (StringUtils.isNotEmpty(contentType)) {
			header = new BasicHeader("Content-type", contentType);
			headers.add(header);
		}
		HttpPost httpPost = null;
		httpPost = new HttpPost(url);
		httpPost.setEntity(resEntity);
		RequestConfig config = RequestConfig.custom().setConnectTimeout(DEFAULT_CONNECT_TIMEOUT)
				.setSocketTimeout(DEFAULT_REQUEST_TIMEOUT).build();
		httpPost.setConfig(config);
		String respXml = sendHttpsRequestByPost(httpPost, contentType);
		log.info("收到的响应报文："+respXml.replace("\r\n", "").replace("\n", ""));
		return respXml;
		// return sendRequest(httpPost,resEntity,headers,true);
	}

	/**
	 * 发送HTTPS POST请求
	 * 
	 * @param 要访问的HTTPS地址,POST访问的参数Map对象
	 * @return 返回响应值
	 * @throws Exception
	 */
	public static final String sendHttpsRequestByPost(HttpPost httpPost, String contentType) throws Exception {
		String responseContent = null;
		CloseableHttpResponse resp = null;
		try {
			httpPost.addHeader("Content-type", contentType);
			resp = httpclient.execute(httpPost);
			if (resp.getStatusLine().getStatusCode() == 200) {
				HttpEntity entity  = resp.getEntity();
				responseContent = EntityUtils.toString(entity, "UTF-8");
				resp.getHeaders("content-type");
			}else {
				HttpEntity entity  = resp.getEntity();
				log.info("响应的异常信息："+EntityUtils.toString(entity, "UTF-8"));
				httpPost.abort();
			}
		} catch (Exception e) {
			throw e;
		} finally {
			try {
				if (resp != null) {
					resp.close();
				}
			} catch (Exception e) {
				log.error("关闭请求连接失败", e);
			}
		}
		return responseContent;
	}

	/**
	 * 
	 * @param httpRequest
	 * @param entity
	 * @param headers
	 * @param checkResponseStatus
	 * @return
	 */
	private static String sendRequest(HttpRequestBase httpRequest, HttpEntity entity, List<Header> headers,
			boolean checkResponseStatus) {
		RequestConfig config = RequestConfig.custom().setConnectTimeout(DEFAULT_REQUEST_TIMEOUT)
				.setSocketTimeout(DEFAULT_CONNECT_TIMEOUT).build();
		httpRequest.setConfig(config);
		httpRequest.setHeader("User-Agent", "okHttp");
		if (httpRequest instanceof HttpEntityEnclosingRequestBase) {
			((HttpEntityEnclosingRequestBase) httpRequest).setEntity(entity);
		}
		if (null != headers) {
			// 添加请求头
			for (Header header : headers) {
				httpRequest.addHeader(header);
			}
		}
		// CloseableHttpClient httpClient = HttpClients.createDefault();

		CloseableHttpClient httpClient = null;
		CloseableHttpResponse response = null;
		String resString = null;
		try {
			HttpsURLConnection.setDefaultHostnameVerifier(new AllowAllHostnameVerifier());
			// SSLSocketFactory.getSocketFactory().setHostnameVerifier(new
			// AllowAllHostnameVerifier());
			SSLContextBuilder builder = new SSLContextBuilder();
			builder.loadTrustMaterial(null, new TrustSelfSignedStrategy());
			SSLConnectionSocketFactory sslsf = new SSLConnectionSocketFactory(builder.build());
			httpClient = HttpClients.custom().setSSLSocketFactory(sslsf).build();
			response = httpClient.execute(httpRequest);
			HttpEntity resEntity = response.getEntity();
			int statusCode = response.getStatusLine().getStatusCode();
			resString = EntityUtils.toString(resEntity, "UTF-8");
			return resString;
		} catch (Exception e) {
			log.error("请求出现异常：", e);
			throw new RuntimeException(resString, e);
		} finally {
			try {
				if (response != null) {
					response.close();
				}
				if (httpRequest != null) {
					httpRequest.releaseConnection();
				}
				if (httpClient != null) {
					httpClient.close();
				}
			} catch (IOException e) {
				log.error("关闭连接异常：", e);

			}
		}
	}

	public static String sendByPost(String url, String content) throws Exception {
		return sendByPost(url, content, "text/plain");
	}

	public static String sendGet(String url,int readTimeOut , int connectTimeOut) throws Exception{
		String result = "";
		BufferedReader in = null;
		try {
			URL realUrl = new URL(url);
			URLConnection connection = realUrl.openConnection();
			connection.setReadTimeout(readTimeOut);
			connection.setConnectTimeout(connectTimeOut);
			connection.connect();
			System.out.println(connection.getHeaderFields());
			Map<String, List<String>> map = connection.getHeaderFields();
			for (String key : map.keySet()) {
				System.out.println(key + "--->" + map.get(key));
			}
			in = new BufferedReader(new InputStreamReader(connection.getInputStream(), "utf-8"));
			String line;
			while ((line = in.readLine()) != null) {
				result += line;
			}
		}finally {
			try {
				if (in != null) {
					in.close();
				}
			} catch (Exception e2) {
				e2.printStackTrace();
			}
		}
		return result;
	}
	public static void main(String[] args) throws Exception {
		while (true) {
			try {
				System.out.println(sendByPost("https://ds.umpay.com/umpaydc/dataQuery/", "<?xml version=\"1.0\" encoding=\"UTF-8\"?><request></request>"));
				Thread.sleep(1000);
			} catch (Exception e) {
				e.printStackTrace();
				Thread.sleep(1000);
			}
			
		}
	}
}
