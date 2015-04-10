package com.profit.laji.lang.util;

import javax.servlet.http.HttpServletRequest;

/**
 * Http辅助工具类
 * @author heyang
 * 2015-03-25
 */
public class HttpUtils {

	/**
	 * @param request
	 * @return
	 */
	public static String getClientIP(HttpServletRequest request) {
		String ip = request.getHeader("x-forwarded-for");
		if (StringUtils.isEmpty(ip) || ip.equalsIgnoreCase("unknown")) {
			ip = request.getHeader("Proxy-Client-IP");
		}
		if (StringUtils.isEmpty(ip) || ip.equalsIgnoreCase("unknown")) {
			ip = request.getHeader("WL-Proxy-Client-IP");
		}
		if (StringUtils.isEmpty(ip) || ip.equalsIgnoreCase("unknown")) {
			ip = request.getRemoteAddr();
		}
		return ip;
	}
	
	/**
	 * 如果通过了多级反向代理的话，X-Forwarded-For的值并不止一个，而是一串Ｉｐ值，究竟哪个才是真正的用户端的真实IP呢？
	 * X-Forwarded-For中第一个非unknown的有效IP字符串。
	 * @param ip
	 * @return
	 */
	public static String getReallyIp(String ip){
		if (ip == null) return "";
		String[] ipArr = ip.split(",");
		String currIp = "";
		for (int i = 0, j = ipArr.length; i < j; i ++){
			currIp = ipArr[i];
			if (!"unknown".equalsIgnoreCase(currIp) && !StringUtils.isEmpty(currIp)) 
				return currIp.trim();
		}
		return currIp;
	}
	
}
