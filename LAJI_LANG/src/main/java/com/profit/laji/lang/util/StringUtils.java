package com.profit.laji.lang.util;

import java.util.List;

import org.apache.poi.ss.formula.functions.T;


/**
 * 字符串辅助类
 * @author heyang
 *
 */
public class StringUtils {
	
	/**
	 * 非空校验
	 * @param str
	 * @return
	 */
	public static boolean isEmpty(String str) {
		return org.apache.commons.lang.StringUtils.isEmpty(str);
	}
	
	/**
	 * 对象非空校验
	 * @param obj
	 * @return
	 */
	public static boolean isNull(Object obj) {
		return null == obj;
	}
	
	/**
	 * 
	 * @return
	 */
	public static boolean isListNull(List list) {
		return null == list || list.isEmpty();
	}
	
	/**
	 * 真假校验
	 * @param str
	 * @return
	 */
	public static boolean isTrue(String str) {
		return "1".equals(str);
	}
	
	/**
	 * 长度校验
	 * @param str
	 * @param length
	 * @return
	 */
	public static boolean isLengthOut(String str, int length) {
		return str.length() > length;
	}
	
	/**
	 * 将null转换为空串，不为null的原值返回
	 * @param str
	 */
	public static String transforNull(Object str) {
		if (str == null)
			return "";
		return String.valueOf(str);
	}
	
	/**
	 * 全角转半角
	 * @param text
	 * @return
	 */
	public static String SBCTODBC(String text) {
		if(!StringUtils.isEmpty(text)){
			char charArr[] = text.toCharArray();
			for (int i = 0; i < charArr.length; i++) {
				if (charArr[i] == '\u3000') {
					charArr[i] = ' ';
				} else if (charArr[i] > '\uFF00' && charArr[i] < '\uFF5F') {
					charArr[i] = (char) (charArr[i] - 65248);
				}
			}
			return new String(charArr);
		}
		return null;
	}
	
	
	/**
	 * 半角转全角 
	 * @param input
	 * @return
	 */
	public static String DBCTOSBC(String input) {
		char c[] = input.toCharArray();
		for (int i = 0; i < c.length; i++) {
        	if (c[i] == ' ') {
        		c[i] = '\u3000';
        	} else if (c[i] < '\177') {
        		c[i] = (char) (c[i] + 65248);
        	}
        }
		return new String(c);
	}
	
}
