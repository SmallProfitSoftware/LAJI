package com.profit.laji.oss.service.system.impl;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.common.collect.Lists;
import com.profit.laji.entity.Result;
import com.profit.laji.entity.exception.BusinessException;
import com.profit.laji.entity.system.Auth;
import com.profit.laji.lang.constant.MsgConstant;
import com.profit.laji.lang.util.CodeUtils;
import com.profit.laji.lang.util.ExcelUtils;
import com.profit.laji.lang.util.FileUtils;
import com.profit.laji.lang.util.StringUtils;
import com.profit.laji.oss.dao.system.AuthDao;
import com.profit.laji.oss.service.system.AuthService;

/**
 * 权限业务接口实现
 * @author heyang
 * 2015-03-05
 */
@Service
public class AuthServiceImpl implements AuthService {

	@Autowired
	private AuthDao authDao;
	
	@Override
	public List<Auth> getAll() {
		return authDao.getAll();
	}

	@Override
	public int add(Auth auth, String pid) {
		if (isExist(auth))
			throw new BusinessException(MsgConstant.EXCEPTION_STR_URL_EXIST);
		auth.setCode(getMaxCode(pid));
		return authDao.add(auth);
	}

	@Override
	public int update(Auth auth) {
		if (isExist(auth))
			throw new BusinessException(MsgConstant.EXCEPTION_STR_URL_EXIST);
		auth.setCode(auth.getId());
		return authDao.update(auth);
	}

	/**
	 * 是否存在
	 * @param auth
	 * @return
	 */
	private boolean isExist(Auth auth) {
		Auth a = authDao.getExist(auth);
		if (!StringUtils.isEmpty(auth.getId())) { //修改
			return !auth.getId().equals(a.getCode());
		}
		return !StringUtils.isNull(a);
	}
	
	/**
	 * 分配编码
	 * @param len
	 * @return
	 */
	private String getMaxCode(String pid) {
		int len = 2;
		String defCode = null;
		if (StringUtils.isEmpty(pid)) {
			len = 2;
			defCode = "01";
		} else {
			len = 2 + pid.length();
			defCode = pid + "01";
		}
		Map<String, String> params = new HashMap<String, String>();
		params.put("pcode", pid);
		params.put("len", len + "");
		String code = authDao.maxCode(params);
		if (StringUtils.isEmpty(code))
			return defCode;
		String pattern = "#";
		for (int i = 0; i < len; i ++) {
			pattern += "0";
		}
		return CodeUtils.nextCode(code, pattern);
	}

	@Override
	public Result<Auth> getResults(Map<String, String> params) {
		int count = authDao.queryCount(params);
		if (count == 0)
			return Result.newInstance(null, 0);
		List<Auth> list = authDao.queryList(params);
		return Result.newInstance(list, count);
	}

	@Override
	public int add(Auth t) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public Auth get(Auth t) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int delete(String id) {
		return authDao.delete(id);
	}

	@Override
	public String _import(File file, String pid, String userId) {
		Workbook workbook = null;
		int rowNum = 0;
		int curRow = 0; // 当前行
		int count = 0;//计数
		FileInputStream input = null;
		try {

			String extension = FileUtils.getExtension(file.getName());
			input = new FileInputStream(file);
			if ("xls".equals(extension)) { // office2003
				workbook = new HSSFWorkbook(input);
			} else if ("xlsx".equals(extension)) { // office2007
				workbook = new XSSFWorkbook(input);
			}
			if (workbook != null) {
				Row row = null;
				Sheet sheet = workbook.getSheetAt(0);
				rowNum = sheet.getLastRowNum();
				String str = null, name = null, url = null;
				String code = getMaxCode(pid);
				
				StringBuffer message = new StringBuffer();
				List<String> strs = Lists.newArrayList();
				List<String> urls = Lists.newArrayList();
				List<Auth> auths = Lists.newArrayList();
				
				for (int i = 1; i <= rowNum; i++) {
					row = sheet.getRow(i);
					curRow = i + 1;
					if (row == null) {
						continue;
					}
					//权限字符串
					str = ExcelUtils.readCellVal(row.getCell(0));
					//权限名称
					name = ExcelUtils.readCellVal(row.getCell(1));
					//权限URL
					url = ExcelUtils.readCellVal(row.getCell(2));
					
					if (StringUtils.isEmpty(str)) {
						message.append("第 " + curRow + " 行，第 1 列，权限字符串为必填项！");
					} else if (str.contains("^[a-zA-Z0-9_]+$")){
						message.append("第 " + curRow + " 行，第 1 列，权限字符串只能是下划线、数字或者字母！");
					} else if (strs.contains(str)) {
						message.append("第 " + curRow + " 行，第 1 列，权限字符串[" + str + "]已存在！");
					}
					strs.add(str);
					
					if (StringUtils.isEmpty(name)) {
						message.append("第 " + curRow + " 行，第 2 列，权限名称为必填项！</br>");
					} 
					if (StringUtils.isEmpty(url)) {
						message.append("第 " + curRow + " 行，第 3 列，权限URL为必填项！</br>");
					} else if (urls.contains(url)) {
						message.append("第 " + curRow + " 行，第 3 列，权限URL[" + url + "]已存在！");
					}
					urls.add(url);
					
					if (StringUtils.isEmpty(message.toString())) {
						auths.add(Auth.newInstance(code, name, str, url));
						code = CodeUtils.nextCode(code, "#000000");
					}
				}
				//判断字符串和url在系统中是否存在
				if (!StringUtils.isListNull(strs)){
					message(message, strs, true);
				}
				if (!StringUtils.isListNull(urls)){
					message(message, urls, false);
				}
				if (!StringUtils.isEmpty(message.toString())) {
					return message.toString();
				}
				//保存
				authDao.add(auths);
				return auths.size() + "";
			}
		} catch (Exception e) {
			e.printStackTrace();
			return "读取execl文档出错！";
		} finally {
			if (input != null) {
				try {
					input.close();
				} catch (IOException e) {
					e.printStackTrace();
					return "关闭文件流出错！";
				}
			}
		}
		return null;
	}
	
	private void message(StringBuffer message, List<String> strs, boolean isStr) {
		Map<String, String> params = new HashMap<String, String>();
		StringBuffer sb = new StringBuffer();
		for (String s : strs) {
			sb.append("'").append(s).append("',");
		}
		String title = null;
		if (!StringUtils.isEmpty(sb.toString())) {
			if (isStr) {
				params.put("strs", sb.substring(0, sb.lastIndexOf(",")));
				title = "权限字符串";
			} else {
				params.put("urls", sb.substring(0, sb.lastIndexOf(",")));
				title = "权限URL";
			}
			List<Auth> as = authDao.getExists(params);
			if (!StringUtils.isListNull(as)) {
				String val = null;
				for (Auth a : as) {
					if (isStr) {
						val = a.getStr();
					} else {
						val = a.getUrl();
					}
					message.append(title + "[" + val + "]在系统中已存在！");
				}
			}
		}
	}

	
}
