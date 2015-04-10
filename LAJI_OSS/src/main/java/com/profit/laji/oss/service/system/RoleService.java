package com.profit.laji.oss.service.system;

import java.util.List;

import com.profit.laji.entity.system.Role;
import com.profit.laji.entity.system.RoleAuth;
import com.profit.laji.oss.service.ServiceSupport;


/**
 * 角色业务接口
 * @author heyang
 * 2015-03-31
 */
public interface RoleService extends ServiceSupport<Role> {
	
	/**
	 * 获取所有角色
	 * @return
	 */
	public List<Role> getAll();
	
	/**
	 * 获取角色权限
	 * @param roleCode
	 * @return
	 */
	public List<RoleAuth> queryRoleAuths(String roleCode);
	
	/**
	 * 更新角色权限信息
	 * @param role
	 * @param codes
	 * @return
	 */
	public int updateRoleAuths(String role, String codes);
	
}
