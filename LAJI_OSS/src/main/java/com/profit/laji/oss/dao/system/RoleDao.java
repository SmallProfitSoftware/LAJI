package com.profit.laji.oss.dao.system;

import java.util.List;
import java.util.Map;

import com.profit.laji.entity.system.Role;
import com.profit.laji.entity.system.RoleAuth;

/**
 * 角色持久层接口
 * @author heyang
 * 2015-03-31
 */
public interface RoleDao {
	
	/**
	 * 获取所有角色
	 * @return
	 */
	public List<Role> getAll();
	
	/**
	 * 根据条件查询列表
	 * @return
	 */
	public List<Role> queryList(Map<String, String> params);
	
	/**
	 * 根据条件查询总数
	 * @param params
	 * @return
	 */
	public int queryCount(Map<String, String> params);
	
	/**
	 * 获取角色权限
	 * @param roleCode
	 * @return
	 */
	public List<RoleAuth> queryRoleAuths(String roleCode);
	
	/**
	 * 保存
	 * @param role
	 * @return
	 */
	public int save(Role role);
	
	/**
	 * 修改
	 * @param role
	 */
	public int update(Role role);
	
	/**
	 * 最大编码
	 * @return
	 */
	public String maxCode();
	
	/**
	 * 获取
	 * @param role
	 * @return
	 */
	public Role get(Role role);
	
	/**
	 * 删除角色权限
	 * @param role
	 * @return
	 */
	public int deleteRA(String role);
	
	/**
	 * 批量增加
	 * @param auths
	 * @return
	 */
	public void add(List<RoleAuth> auths);
	
}
