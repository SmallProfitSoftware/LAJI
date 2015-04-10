package com.profit.laji.lang.util;

import java.text.DecimalFormat;

/**
 * 编码辅助类
 * @author heyang
 * 2015-03-27
 */
public class CodeUtils {
	
	private static final DecimalFormat df = new DecimalFormat();
	
	public static String nextCode(String currCode, String pattern){
		int curr_code = 1;
		if (!StringUtils.isEmpty(currCode)) {
			curr_code = Integer.parseInt(currCode) + 1;
		}
		df.applyPattern(pattern);
		return df.format(curr_code);
	}
	
	public static int nextNum(int num){
		return num + 1;
	}
	
	public static void main(String[] args) {
		System.out.println(CodeUtils.nextCode("0001", "#0000"));
	}
	
}
