package com.profit.laji.oss.dao.system;

import java.util.List;
import java.util.Map;

import com.profit.laji.entity.system.User;

/**
 * 用户持久层接口
 * @author heyang
 * 2015-03-24
 */
public interface UserDao {
	
	/**
	 * 获取所有
	 * @return
	 */
	public List<User> getAll();
	
	/**
	 * 根据条件查询列表
	 * @return
	 */
	public List<User> queryList(Map<String, String> params);
	
	/**
	 * 根据条件查询总数
	 * @param params
	 * @return
	 */
	public int queryCount(Map<String, String> params);
	
	/**
	 * 保存
	 * @param user
	 * @return
	 */
	public int save(User user);
	
	/**
	 * 修改
	 * @param user
	 */
	public int update(User user);
	
	/**
	 * 更新最后一次登陆信息
	 * @param user
	 * @return
	 */
	public int updateLastLogin(User user);
	
	/**
	 * 根据ID删除
	 * @param id
	 */
	public int delete(String userId);
	
	/**
	 * 根据条件删除
	 * @param params
	 */
	public int delete(Map<String, String> params);
	
	/**
	 * 根据ID获取用户
	 * @param userId
	 * @return
	 */
	public User get(String userId);
	
	/**
	 * 锁定/解锁
	 * @param user
	 * @return
	 */
	public int lock(User user);
	
	/**
	 * 密码修改/重置
	 * @param user
	 * @return
	 */
	public int updatePwd(User user);
}
