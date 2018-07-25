package com.umpay.proxyservice.fileBatch.util;

public class ThreadUtil {

	public static void sleep(int times){
		try {
			Thread.sleep(times);
		} catch (InterruptedException e) {
		}
	}
}
