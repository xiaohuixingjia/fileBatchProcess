package com.umpay.proxyservice.fileBatch.util;

import java.net.URL;
import java.net.URLClassLoader;

import com.umpay.proxyservice.constant.Retcode;
import com.umpay.proxyservice.exception.BaseException;

public class DynamicGenerObj {
	@SuppressWarnings({ "unchecked", "resource" })
	public static <T> T gener(Class<T> cs,String jarPath,String className) throws BaseException{
		try {
			URL url = new URL("file:" + jarPath);
			URLClassLoader myClassLoader1=new URLClassLoader(new URL[]{url}, Thread.currentThread().getContextClassLoader());
			Class<?> myClass = null;
			myClass = myClassLoader1.loadClass(className);
			return (T) myClass.newInstance();
		} catch (Exception e) {
			throw new BaseException(Retcode.CLASS_INIT_FAIL, e);
		}
	}
}
