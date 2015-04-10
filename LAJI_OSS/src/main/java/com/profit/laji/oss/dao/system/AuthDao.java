package com.profit.laji.oss.dao.system;

import java.util.List;
import java.util.Map;

import com.profit.laji.entity.system.Auth;

/**
 * 权限持久层接口
 * @author heyang
 * 2015-03-24
 */
public interface AuthDao {
	
	/**
	 * 获取所有
	 * @return
	 */
	public List<Auth> getAll();
	
	/**
	 * 根据条件查询列表
	 * @return
	 */
	public List<Auth> queryList(Map<String, String> params);
	
	/**
	 * 根据条件查询总数
	 * @param params
	 * @return
	 */
	public int queryCount(Map<String, String> params);
	
	/**
	 * 批量新增
	 * @param auth
	 * @return
	 */
	public int add(Auth auth);
	
	/**
	 * 批量保存
	 * @param auths
	 */
	public void add(List<Auth> auths);
	
	/**
	 * 更新
	 * @param auth
	 * @return
	 */
	public int update(Auth auth);
	
	/**
	 * 是否存在
	 * @param auth
	 * @return
	 */
	public Auth getExist(Auth auth);
	
	/**
	 * 获取最大编码
	 * @param params
	 * @return
	 */
	public String maxCode(Map<String, String> params);
	
	/**
	 * 删除
	 * @param id
	 * @return
	 */
	public int delete(String id);
	
	/**
	 * 获取存在数量
	 * @param params
	 * @return
	 */
	public List<Auth> getExists(Map<String, String> params);
}
