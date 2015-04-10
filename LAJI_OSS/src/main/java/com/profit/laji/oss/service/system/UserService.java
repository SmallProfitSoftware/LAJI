package com.profit.laji.oss.service.system;

import java.util.List;

import com.profit.laji.entity.system.Auth;
import com.profit.laji.entity.system.User;
import com.profit.laji.oss.service.ServiceSupport;

/**
 * 用户业务接口
 * @author heyang
 * 2015-03-05
 */
public interface UserService extends ServiceSupport<User> {
	
	/**
	 * 获取所有
	 * @return
	 */
	public List<User> getAll();
	
	/**
	 * 获取
	 * @param userId
	 * @return
	 */
	public User get(String userId);
	
	/**
	 * 登录
	 * @param user
	 * @return
	 */
	public boolean login(User user);
	
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
	
	/**
	 * 密码校验
	 * @param user
	 * @return
	 */
	public boolean checkPwd(User user);
}
