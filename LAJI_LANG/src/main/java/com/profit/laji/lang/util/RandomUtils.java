package com.profit.laji.lang.util;

import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Random;
import java.util.UUID;

/**
 * 随机辅助类
 * @author heyang
 *
 */
public final class RandomUtils {
	
	private static final char[] pwdChar = {'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n',
		'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z',
		'0', '1', '2', '3', '4', '5', '6', '7', '8', '9'};
	
	private static Random random = null;
	
	static{
		try {
			random = SecureRandom.getInstance("SHA1PRNG");
		} catch (NoSuchAlgorithmException e) {
			random = new java.security.SecureRandom();
		}
	}
	
	private RandomUtils(){}
	
	/**
	 * 随机生成指定位数的密码
	 * @param len
	 * @return
	 */
	public static String randomPwd(int len){
		StringBuffer pwdApp = new StringBuffer();
		int cLen = pwdChar.length;
		int index = 0;
		while(index < len) {
			int num = random.nextInt(cLen - 1);
			char c = pwdChar[num];
			if (random.nextBoolean()){
				pwdApp.append(String.valueOf(c).toUpperCase());
			} else {
				pwdApp.append(String.valueOf(c));
			}
			index ++;
		}
		return pwdApp.toString();
	}
	
	/**
	 * 随机生成指定位数的纯数字随机数
	 * @param len
	 * @return
	 */
	public static String randomNumber(int len) {
		StringBuffer randomApp = new StringBuffer();
		int index = 0;
		while(index < len) {
			randomApp.append(String.valueOf(random.nextInt(10)));
			index ++;
		}
		return randomApp.toString();
	}
	
	public static String uuid(){
		return UUID.randomUUID().toString();
	}
	
	public static String md5UUID(){
		return MD5Encoder.encode(uuid());
	}
	
}
