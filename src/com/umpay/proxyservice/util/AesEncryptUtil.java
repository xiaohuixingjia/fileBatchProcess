package com.umpay.proxyservice.util;

import java.util.UUID;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

import org.apache.commons.codec.binary.Base64;

import com.umpay.proxyservice.Constant;

public class AesEncryptUtil {
	/**
	 * 加密方法
	 * 
	 * @param sSrc
	 *            待加密信息
	 * @param sKey
	 *            key
	 * @param ivString
	 * @return 由key加密后的信息
	 * @throws Exception
	 */
	public static String encrypt(String sSrc, String sKey, String ivString) throws Exception {
		if (sKey == null) {
			return null;
		}
		byte[] raw = sKey.getBytes();
		SecretKeySpec skeySpec = new SecretKeySpec(raw, "AES");
		Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
		IvParameterSpec iv = new IvParameterSpec(ivString.getBytes());
		cipher.init(Cipher.ENCRYPT_MODE, skeySpec, iv);
		byte[] encrypted = cipher.doFinal(sSrc.getBytes(Constant.UTF_8));
		return Base64.encodeBase64String(encrypted);
	}

	
	public static void main(String[] args) throws Exception {
		System.out.println(UUID.randomUUID().toString());
		String encrypt = encrypt("aabbasdfasdfsaccd", "f9f6da61-9dfb-42", "8b-a1a8-be121323");
		System.out.println(encrypt);
		System.out.println(decrypt(encrypt, "f9f6da61-9dfb-42", "8b-a1a8-be121323"));
	}
	/**
	 * 解密信息
	 * 
	 * @param sSrc
	 *            待解密的信息
	 * @param sKey
	 *            解密密匙
	 * @param ivString
	 * @return
	 * @throws Exception
	 */
	public static String decrypt(String sSrc, String sKey, String ivString) throws Exception {
		try {
			if (sKey == null) {
				return null;
			}
			if (sKey.length() != 16) {
				return null;
			}
			byte[] raw = sKey.getBytes("ASCII");
			SecretKeySpec skeySpec = new SecretKeySpec(raw, "AES");
			Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
			IvParameterSpec iv = new IvParameterSpec(ivString.getBytes());
			cipher.init(Cipher.DECRYPT_MODE, skeySpec, iv);
			byte[] encrypted1 = new Base64().decode(sSrc);
			try {
				byte[] original = cipher.doFinal(encrypted1);
				return new String(original, Constant.UTF_8);
			} catch (Exception e) {
				return null;
			}
		} catch (Exception ex) {
			return null;
		}
	}
}
