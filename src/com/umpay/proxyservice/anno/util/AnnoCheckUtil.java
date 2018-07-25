package com.umpay.proxyservice.anno.util;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.umpay.proxyservice.anno.CheckTypeAnno;
import com.umpay.proxyservice.anno.Require;
import com.umpay.proxyservice.anno.StringCheck;
import com.umpay.proxyservice.anno.exception.AnnoException;
import com.umpay.proxyservice.anno.tacticsService.BaseAnnoCheckService;
import com.umpay.proxyservice.anno.tacticsService.impl.RequireAnnoCheckImpl;
import com.umpay.proxyservice.anno.tacticsService.impl.StringLengthCheckImpl;
import com.umpay.proxyservice.handler.ProcessExecuteHandler.ProcessType;
import com.umpay.proxyservice.util.ClassUtil;

/**
 * 自定义标签验证工具类 (单例模式需要先调用getAnnoCheckUtil方法获取对象)
 * 
 * @author xuxiaojia
 */
public class AnnoCheckUtil {
	private static AnnoCheckUtil anno = new AnnoCheckUtil();
	/*
	 * 组分离的自定义注解
	 */
	private Class<? extends Annotation> groupAnnoClass = CheckTypeAnno.class;
	/**
	 * 自定义注解和自定义注解验证器的map对象
	 */
	private final Map<Class<? extends Annotation>, BaseAnnoCheckService> annoCheckMap = new HashMap<Class<? extends Annotation>, BaseAnnoCheckService>();

	private AnnoCheckUtil() {
		annoCheckMap.put(Require.class, new RequireAnnoCheckImpl());
		annoCheckMap.put(StringCheck.class, new StringLengthCheckImpl());
	}

	public static AnnoCheckUtil getAnnoCheckUtil() {
		return anno;
	}

	/**
	 * 对对象进行制定自定义标签的验证 默认所有类型都校验
	 * 
	 * @param t
	 *            待验证的对象
	 * @param authAnnos
	 *            需要验证的自定义标签的集合(若传空则不进行验证)
	 * @throws AnnoException
	 *             自定义的注解校验异常
	 */
	public <T> void validateAllAnno(T t) throws AnnoException {
		validateSpecifyAnno(t, null, new ArrayList<Class<? extends Annotation>>(annoCheckMap.keySet()));
	}

	/**
	 * 对对象进行制定自定义标签的验证
	 * 
	 * @param t
	 *            待验证的对象
	 * @param authAnnos
	 *            需要验证的自定义标签的集合(若传空则不进行验证)
	 * @throws AnnoException
	 *             自定义的注解校验异常
	 */
	public <T> void validateAllAnno(T t, ProcessType checkType) throws AnnoException {
		validateSpecifyAnno(t, checkType, new ArrayList<Class<? extends Annotation>>(annoCheckMap.keySet()));
	}

	/**
	 * 对对象进行指定自定义标签的验证
	 * 
	 * @param t
	 *            待验证的对象
	 * @param specifyAnnos
	 *            需要验证的自定义标签的集合(若传null或空集合则不进行验证)
	 * @throws AnnoException
	 *             自定义的注解校验异常
	 */
	public <T> void validateSpecifyAnno(T t, ProcessType processType, List<Class<? extends Annotation>> specifyAnnos)
			throws AnnoException {
		if (specifyAnnos != null && specifyAnnos.size() > 0) {
			Field[] fields = ClassUtil.getClassUtil().getClassFields(t.getClass());
			// 遍历所有字段并判断字段是否符合注解标准
			for (Field field : fields) {
				Object fieldValue = ClassUtil.getClassUtil().getFieldValue(field, t);
				for (Class<? extends Annotation> class1 : specifyAnnos) {
					if (field.isAnnotationPresent(class1)) {
						Annotation annotation = field.getAnnotation(class1);
						if (annoCheckMap.containsKey(class1)) {
							CheckTypeAnno typeAnno = (CheckTypeAnno) field.getAnnotation(groupAnnoClass);
							// 校验组判断
							if (groupCheck(processType, typeAnno)) {
								annoCheckMap.get(class1).check(field.getName(), fieldValue, annotation);
							}
						}
					}
				}
			}
		}
	}

	/**
	 * 校验是否属于同组
	 * @param checkType  -- 待判断分组
	 * @param typeAnno   -- 字段注解
	 * @return
	 */
	private boolean groupCheck(ProcessType checkType, CheckTypeAnno typeAnno) {
		if (checkType == null)
			return true;
		else if (typeAnno == null || typeAnno.value() == null) {
			return false;
		} else {
			ProcessType[] checkTypeArray = typeAnno.value();
			for (ProcessType type : checkTypeArray) {
				if (type == checkType)
					return true;
			}
		}
		return false;
	}

}
