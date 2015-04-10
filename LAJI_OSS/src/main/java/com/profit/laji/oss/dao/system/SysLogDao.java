package com.profit.laji.oss.dao.system;

import java.util.List;
import java.util.Map;

import com.profit.laji.entity.system.SysLog;

/**
 * 系统日志持久层接口
 * @author heyang
 * 2015-03-25
 */
public interface SysLogDao {
	
	/**
	 * 新增
	 * @param log
	 * @return
	 */
	public int add(SysLog log);
	
	/**
	 * 查询列表
	 * @param params
	 * @return
	 */
	public List<SysLog> queryList(Map<String, String> params);
	
	/**
	 * 查询数量
	 * @param params
	 * @return
	 */
	public int queryCount(Map<String, String> params);

}
