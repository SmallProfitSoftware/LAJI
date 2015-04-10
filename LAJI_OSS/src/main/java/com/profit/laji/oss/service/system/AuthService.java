package com.profit.laji.oss.service.system;

import java.io.File;
import java.util.List;

import com.profit.laji.entity.system.Auth;
import com.profit.laji.oss.service.ServiceSupport;


/**
 * 权限业务接口
 * @author heyang
 * 2015-03-05
 */
public interface AuthService extends ServiceSupport<Auth> {
	
	/**
	 * 获取所有
	 * @return
	 */
	public List<Auth> getAll();
	
	/**
	 * 新增
	 * @param auth
	 * @param pid
	 * @return
	 */
	public int add(Auth auth, String pid);
	
	/**
	 * 更新
	 */
	public int update(Auth auth);
	
	/**
	 * 导入权限
	 * @param file
	 * @param pid
	 * @param userId
	 * @return
	 */
	public String _import(File file, String pid, String userId);
	
}
