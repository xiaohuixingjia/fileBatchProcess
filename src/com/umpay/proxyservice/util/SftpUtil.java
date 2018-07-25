package com.umpay.proxyservice.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;

import com.jcraft.jsch.Channel;
import com.jcraft.jsch.ChannelSftp;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.Session;
import com.umpay.proxyservice.constant.Retcode;
import com.umpay.proxyservice.exception.BaseException;

/**
 * sftp实现文件的上传下载
 * 
 * @author xuxiaojia
 */
public class SftpUtil {
	/**
	 * session配置的key
	 */
	private final static String SESSION_CONFIG_KEY = "StrictHostKeyChecking";
	/**
	 * session配置的value
	 */
	private final static String SESSION_CONFIG_VALUE = "no";
	/**
	 * session连接时的超时时间设置
	 */
	private final static int SESSION_CONNECT_TIME_OUT = 30000;
	/**
	 * channel连接时的超时时间设置
	 */
	private final static int CHANNEL_CONNECT_TIME_OUT = 1000;
	/**
	 * 通道类型
	 */
	private final static String CHANNEL_TYPE = "sftp";
	/**
	 * 上传标识
	 */
	private final static int UPLOAD_FALG = 1;
	/**
	 * 下载标识
	 */
	private final static int DOWNLOAD_FALG = 2;

	/**
	 * 
	 * @param ip
	 *            主机IP
	 * @param user
	 *            主机登陆用户名
	 * @param psw
	 *            主机登陆密码
	 * @param port
	 *            主机ssh2登陆端口，如果取默认值，传-1
	 * @param ftpFullFileName
	 *            ftp服务端的文件名全路径
	 * @param localFullFileName
	 *            本地文件名称全路径
	 * @throws Exception
	 */
	public static void sshSftpUpload(String ip, String user, String psw, int port, String ftpFullFileName,
			String localFullFileName) throws BaseException {
		sshSftp(ip, user, psw, port, ftpFullFileName, localFullFileName, UPLOAD_FALG);
	}

	/**
	 * 
	 * @param ip
	 *            主机IP
	 * @param user
	 *            主机登陆用户名
	 * @param psw
	 *            主机登陆密码
	 * @param port
	 *            主机ssh2登陆端口，如果取默认值，传-1
	 * @param ftpFullFileName
	 *            ftp服务端的文件名全路径
	 * @param localFullFileName
	 *            本地文件名称全路径
	 * @return
	 * @throws Exception
	 */
	public static void sshSftpDownload(String ip, String user, String psw, int port, String ftpFullFileName,
			String localFullFileName) throws BaseException {
		sshSftp(ip, user, psw, port, ftpFullFileName, localFullFileName, DOWNLOAD_FALG);
	}

	/**
	 * 利用JSch包实现SFTP下载、上传文件
	 * 
	 * @param ip
	 *            主机IP
	 * @param user
	 *            主机登陆用户名
	 * @param psw
	 *            主机登陆密码
	 * @param port
	 *            主机ssh2登陆端口，如果取默认值，传-1
	 * @param ftpFullFileName
	 *            ftp服务端的文件名全路径
	 * @param localFullFileName
	 *            本地文件名称全路径
	 * @param flag
	 *            上传下载的标识 1--为上传 2--为下载
	 * @return
	 */
	private static void sshSftp(String ip, String user, String psw, int port, String ftpFullFileName,
			String localFullFileName, int flag) throws BaseException {
		Session session = null;
		Channel channel = null;

		JSch jsch = new JSch();
		try {
			if (port <= 0) {
				// 连接服务器，采用默认端口
				session = jsch.getSession(user, ip);
			} else {
				// 采用指定的端口连接服务器
				session = jsch.getSession(user, ip, port);
			}
			// 如果服务器连接不上，则抛出异常
			if (session == null) {
				throw new BaseException(Retcode.INNER_ERROR, "ftp服务器无法连接，用户：" + user + " ip:" + ip + " 端口：" + port);
			}
			// 设置登陆主机的密码
			session.setPassword(psw);// 设置密码
			// 设置第一次登陆的时候提示，可选值：(ask | yes | no)
			session.setConfig(SESSION_CONFIG_KEY, SESSION_CONFIG_VALUE);
			// 设置登陆超时时间
			session.connect(SESSION_CONNECT_TIME_OUT);
				channel = execute(ftpFullFileName, localFullFileName, flag, session);
		} catch (BaseException e) {
			throw e;
		} catch (Exception e) {
			throw new BaseException(Retcode.INNER_ERROR, "sftp" + (flag == UPLOAD_FALG ? "上传" : "下载")
					+ "文件出错,ftp文件名全路径：" + ftpFullFileName + ",本地文件名全路径：" + localFullFileName, e);
		} finally {
			if (session != null) {
				session.disconnect();
			}
			if (channel != null) {
				channel.disconnect();
			}
		}
	}

	/**
	 * 开始执行上传下载
	 * 
	 * @param ftpFullFileName
	 *            ftp服务端的文件名全路径
	 * @param localFullFileName
	 *            本地文件名称全路径
	 * @param flag
	 *            上传下载的标识 1--为上传 2--为下载
	 * @param session
	 *            连接
	 * @return
	 * @throws Exception
	 */
	private static Channel execute(String ftpFullFileName, String localFullFileName, int flag, Session session)
			throws Exception {
		// 创建sftp通信通道
		Channel channel = (Channel) session.openChannel(CHANNEL_TYPE);
		channel.connect(CHANNEL_CONNECT_TIME_OUT);
		ChannelSftp sftp = (ChannelSftp) channel;
		// 以下代码实现从本地上传一个文件到服务器，如果要实现下载，对换以下流就可以了
		OutputStream outstream = null;
		InputStream instream = null;
		try {
			switch (flag) {
			case UPLOAD_FALG:
				outstream = sftp.put(ftpFullFileName, ChannelSftp.OVERWRITE);
				instream = new FileInputStream(new File(localFullFileName));
				break;
			case DOWNLOAD_FALG:
				outstream = new FileOutputStream(new File(localFullFileName));
				try {
					instream = sftp.get(ftpFullFileName);
				} catch (Exception e) {
					throw new BaseException(Retcode.FILE_NOT_FOUND, "在ftp没有找到当前文件名的文件：" + ftpFullFileName, e);
				}
				break;
			}
			byte b[] = new byte[1024];
			int n;
			while ((n = instream.read(b)) != -1) {
				outstream.write(b, 0, n);
				outstream.flush();
			}
			return channel;
		} catch (Exception e) {
			throw e;
		} finally {
			if (outstream != null) {
				outstream.flush();
				outstream.close();
			}
			if (instream != null) {
				instream.close();
			}
		}
	}

	/**
	 * 利用JSch包实现SFTP下载、上传文件
	 * 
	 * @param ip
	 *            主机IP
	 * @param user
	 *            主机登陆用户名
	 * @param psw
	 *            主机登陆密码
	 * @param port
	 *            主机ssh2登陆端口，如果取默认值(默认值22)，传-1
	 * @param privateKey
	 *            密钥文件路径
	 * @param passphrase
	 *            密钥的密码
	 * 
	 */
	/*
	 * public static void sshSftp(String ip, String user, String psw, int port,
	 * String privateKey, String passphrase) throws Exception { Session session
	 * = null; Channel channel = null;
	 * 
	 * JSch jsch = new JSch();
	 * 
	 * // 设置密钥和密码 if (privateKey != null && !"".equals(privateKey)) { if
	 * (passphrase != null && "".equals(passphrase)) { // 设置带口令的密钥
	 * jsch.addIdentity(privateKey, passphrase); } else { // 设置不带口令的密钥
	 * jsch.addIdentity(privateKey); } }
	 * 
	 * if (port <= 0) { // 连接服务器，采用默认端口 session = jsch.getSession(user, ip); }
	 * else { // 采用指定的端口连接服务器 session = jsch.getSession(user, ip, port); }
	 * 
	 * // 如果服务器连接不上，则抛出异常 if (session == null) { throw new Exception(
	 * "session is null"); }
	 * 
	 * // 设置登陆主机的密码 session.setPassword(psw);// 设置密码 // 设置第一次登陆的时候提示，可选值：(ask |
	 * yes | no) session.setConfig("StrictHostKeyChecking", "no"); // 设置登陆超时时间
	 * session.connect(30000);
	 * 
	 * try { // 创建sftp通信通道 channel = (Channel) session.openChannel("sftp");
	 * channel.connect(1000); ChannelSftp sftp = (ChannelSftp) channel;
	 * 
	 * // 进入服务器指定的文件夹 sftp.cd("domains");
	 * 
	 * // 列出服务器指定的文件列表 // Vector v = sftp.ls("*.txt"); // for (int i = 0; i <
	 * v.size(); i++) { // System.out.println(v.get(i)); // }
	 * 
	 * // 以下代码实现从本地上传一个文件到服务器，如果要实现下载，对换以下流就可以了 OutputStream outstream =
	 * sftp.put("1.txt"); InputStream instream = new FileInputStream(new
	 * File("c:/print.txt"));
	 * 
	 * byte b[] = new byte[1024]; int n; while ((n = instream.read(b)) != -1) {
	 * outstream.write(b, 0, n); }
	 * 
	 * outstream.flush(); outstream.close(); instream.close(); } catch
	 * (Exception e) { e.printStackTrace(); } finally { session.disconnect();
	 * channel.disconnect(); } }
	 */
}
