package com.profit.laji.lang.util;

import java.text.SimpleDateFormat;
import java.util.Date;

public class TimerUtils {
	/**
	 * 日期格式化
	 */
	private static final String DEFAULT_FORMAT = "yyyy-MM-dd HH:mm:ss";
	
	/**
	 * 日期格式化类
	 */
	private static final SimpleDateFormat sdf = new SimpleDateFormat(DEFAULT_FORMAT);
	
	/**
	 * 获取当前日期
	 * @return
	 */
	public static Date getCurrentDate() {
		return new Date();
	}
	
	/**
	 * 获取当前日期字符串
	 * @return
	 */
	public static String getCurrentTime() {
		return getCurrentTime(DEFAULT_FORMAT);
	}
	
	/**
	 * 获取当前指定格式日期字符串
	 * @return
	 */
	public static String getCurrentTime(String format) {
		return format(getCurrentDate(), format);
	}
	
	/**
	 * 日期格式化
	 * @param date
	 * @param format
	 * @return
	 */
	public static String format(Date date, String format) {
		if (date == null)
			date = getCurrentDate();
		if (StringUtils.isEmpty(format))
			format = DEFAULT_FORMAT;
		sdf.applyPattern(format);
		return sdf.format(date);
	}
	
}
