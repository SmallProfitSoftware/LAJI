package com.profit.laji.lang.util;

import java.io.File;
import java.io.InputStream;
import java.util.Arrays;
import java.util.List;
import java.util.Properties;
import java.util.Set;

import org.springframework.web.multipart.MultipartFile;

/**
 * 文件辅助类
 * @author heyang
 *
 */
public class FileUtils {
	
	/**
	 * 文件名称格式
	 */
	public static final String FILENAME_FORMAT = "yyyyMMddHHmmssSSS";
	
	
	/**
	 * 获取文件扩展名
	 * 
	 * @param fileName
	 * @return
	 * @throws Exception
	 */
	public static String getExtension(String fileName) throws Exception {
		return fileName.lastIndexOf(".") == -1 ? "" : fileName
				.substring(fileName.lastIndexOf(".") + 1);
	}
	
	/**
	 * 文件重命名
	 * 
	 * @param extension
	 * @return
	 */
	public static String rename(String fileName) throws Exception {
		String extension = getExtension(fileName);
		return TimerUtils.getCurrentTime(FILENAME_FORMAT) + "." + extension;
	}
	
	/**
	 * 转移文件
	 * 
	 * @param file
	 * @param path
	 * @param fileName
	 * @return
	 * @throws Exception
	 */
	public static File transferTo(MultipartFile file, String path,
			String fileName) throws Exception {
		File targetFile = new File(path, fileName);
		if (!targetFile.exists()) {
			targetFile.mkdirs();
		}
		// 保存
		try {
			file.transferTo(targetFile);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return targetFile;
	}
	
	/**
	 * 读取属性文件，返回list(返回的结果集中只包含key)
	 * @param fileName
	 * @return
	 * @throws Exception
	 */
	public static List<String> readProps(String fileName) throws Exception{
		fileName = "/" + fileName + ".properties";
		InputStream inputStream = null;
		Properties prop = new Properties();
		try {
			inputStream = FileUtils.class.getResourceAsStream(fileName);
			prop.load(inputStream);
			Set<Object> keySet = prop.keySet();
			String[] keyArr = keySet.toArray(new String[]{});
			return Arrays.asList(keyArr);
		} catch (Exception e) {
			e.printStackTrace();
		} finally{
			try {
				inputStream.close();
			} catch (Exception e2) {
				e2.printStackTrace();
			}
		}
		return null;
	}
	
}
