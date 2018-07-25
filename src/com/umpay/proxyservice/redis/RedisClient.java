package com.umpay.proxyservice.redis;

import java.util.List;
import java.util.concurrent.TimeUnit;

import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;

/**
 * 给予redis的缓存实现
 * 
 * @author xuxiaojia
 */
public class RedisClient {
	/**
	 * 毫秒秒类型
	 */
	public static final int MICROSECONDS_TYPE = 0;
	/**
	 * 秒类型
	 */
	public static final int SECONDS_TYPE = 1;
	/**
	 * 分类型
	 */
	public static final int MINUTES_TYPE = 3;
	/**
	 * 小时类型
	 */
	public static final int HOURS_TYPE = 5;
	/**
	 * 天类型
	 */
	public static final int DAYS_TYPE = 7;
	/*
	 * 从redis list中取数据时的阻塞时间
	 */
	private long backupTime;
	/*
	 * 从redis list中取数据时的阻塞时间类型
	 */
	private int backupTimeType;
	/*
	 * 调用setEx方法时默认的缓存时间
	 */
	private long cacheTime;
	/*
	 * 缓存设置时效时的默认时间类型
	 */
	private int cacheTimeType;

	private RedisTemplate<String, String> stringRedisTemplate;

	public void setStringRedisTemplate(RedisTemplate<String, String> stringRedisTemplate) {
		this.stringRedisTemplate = stringRedisTemplate;
	}


	public RedisClient(long backupTime, int backupTimeType, long cacheTime, int cacheTimeType) {
		super();
		this.backupTime = backupTime;
		this.backupTimeType = backupTimeType;
		this.cacheTime = cacheTime;
		this.cacheTimeType = cacheTimeType;
	}


	/**
	 * 根据时间类型获取timeUnit枚举
	 * 
	 * @param type
	 *            事件类型
	 * @return
	 */
	public TimeUnit getTimeUnitByTimeType(int type) {
		switch (type) {
		case MICROSECONDS_TYPE:
			return TimeUnit.MICROSECONDS;
		case SECONDS_TYPE:
			return TimeUnit.SECONDS;
		case MINUTES_TYPE:
			return TimeUnit.MINUTES;
		case HOURS_TYPE:
			return TimeUnit.HOURS;
		case DAYS_TYPE:
			return TimeUnit.DAYS;
		default:
			return TimeUnit.SECONDS;
		}
	}

	/**
	 * 从队列中取出数据
	 * 
	 * @param listName
	 * @return
	 */
	public String rpop(String listName) {
		return stringRedisTemplate.opsForList().rightPop(listName, backupTime, getTimeUnitByTimeType(backupTimeType));
	}
	
	/**
	 * 获取链表中的
	 * @param listName
	 * @return
	 */
	public List<String> lrang(String listName){
		return stringRedisTemplate.opsForList().range(listName, 0, -1);
	}

	/**
	 * 将信息存入队列
	 * 
	 * @param listName
	 * @param value
	 */
	public void lPush(String listName, String value) {
		stringRedisTemplate.opsForList().leftPush(listName, value);
	}

	/**
	 * 返回当前集合的长度
	 * 
	 * @param listName
	 * @return
	 */
	public Long listSize(String listName) {
		return stringRedisTemplate.opsForList().size(listName);
	}

	/**
	 * 将消息永久set到redis中
	 * 
	 * @param k
	 * @param v
	 */
	public void set(String k, String v) {
		ValueOperations<String, String> opsForValue = stringRedisTemplate.opsForValue();
		opsForValue.set(k, v);
	}

	/**
	 * 根据k信息获取存在redis中的value
	 * 
	 * @param k
	 * @return
	 */
	public String get(String k) {
		ValueOperations<String, String> opsForValue = stringRedisTemplate.opsForValue();
		return opsForValue.get(k);
	}

	/**
	 * set值到redis并设置有效时间
	 * 
	 * @param k
	 * @param v
	 * @param cacheTime
	 *            有效时间
	 * @param timeUnit
	 *            有效时间类型
	 * @return
	 */
	public boolean setEx(String k, String v, long cacheTime, TimeUnit timeUnit) {
		ValueOperations<String, String> opsForValue = stringRedisTemplate.opsForValue();
		opsForValue.set(k, v, cacheTime, timeUnit);
		return true;
	}

	/**
	 * set值到redis并设置有效时间
	 * 
	 * @param k
	 * @param v
	 * @return
	 */
	public boolean setEx(String k, String v) {
		return setEx(k, v, cacheTime, getTimeUnitByTimeType(cacheTimeType));
	}

	/**
	 * 删除key
	 * @param k
	 */
	public void del(String k){
		stringRedisTemplate.delete(k);
	}
	
	/**
	 * 在list中删除所有和value一样的数据
	 * @param listName
	 * @param value
	 */
	public void lrem(String listName,String value){
		stringRedisTemplate.opsForList().remove(listName, 0, value);
	}
	
}
