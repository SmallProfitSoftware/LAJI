package com.profit.laji.oss.service.system.impl;


import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.profit.laji.entity.Result;
import com.profit.laji.entity.system.SysLog;
import com.profit.laji.oss.dao.system.SysLogDao;
import com.profit.laji.oss.service.system.SysLogService;

/**
 * 用户业务接口实现
 * 
 * @author heyang 2015-03-05
 */
@Service
public class SysLogServiceImpl implements SysLogService {

	public static Logger LOG = LoggerFactory.getLogger(SysLogService.class);
	
	@Autowired
	private SysLogDao sysLogDao;

	@Override
	public int add(SysLog log) {
		return sysLogDao.add(log);
	}

	@Override
	public Result<SysLog> getResults(Map<String, String> params) {
		int count = sysLogDao.queryCount(params);
		if (count == 0)
			return Result.newInstance(null, 0);
		List<SysLog> list = sysLogDao.queryList(params);
		return Result.newInstance(list, count);
	}

	@Override
	public SysLog get(SysLog t) {
		return null;
	}

	@Override
	public int delete(String id) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int update(SysLog t) {
		// TODO Auto-generated method stub
		return 0;
	}

}
