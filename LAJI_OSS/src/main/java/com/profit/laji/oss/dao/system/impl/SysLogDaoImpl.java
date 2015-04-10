package com.profit.laji.oss.dao.system.impl;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.profit.laji.entity.system.SysLog;
import com.profit.laji.oss.dao.DaoSupport;
import com.profit.laji.oss.dao.system.SysLogDao;

/**
 * 日志持久层实现
 * @author heyang
 * 2015-03-25
 */
@Repository
public class SysLogDaoImpl extends DaoSupport<SysLog> implements SysLogDao {

	@Override
	public int add(SysLog log) {
		return insert(log);
	}

	@Override
	public List<SysLog> queryList(Map<String, String> params) {
		return getSqlSession().selectList(getNamespace().concat(".queryList"), params);
	}

	@Override
	public int queryCount(Map<String, String> params) {
		return getSqlSession().selectOne(getNamespace().concat(".queryCount"), params);
	}

}
