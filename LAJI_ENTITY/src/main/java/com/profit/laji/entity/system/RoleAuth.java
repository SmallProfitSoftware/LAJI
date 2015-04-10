package com.profit.laji.entity.system;

import java.io.Serializable;

/**
 * 后台角色和权限关联
 * @author heyang
 * 2015-02-12
 */
public class RoleAuth implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 3251332455368646221L;

	/**
	 * 角色编码
	 */
	private String roleCode;
	
	/**
	 * 权限编码
	 */
	private String authCode;
	
	private RoleAuth(){}
	
	private RoleAuth(String roleCode, String authCode) {
		this.roleCode = roleCode;
		this.authCode = authCode;
	}

	public String getRoleCode() {
		return roleCode;
	}

	public void setRoleCode(String roleCode) {
		this.roleCode = roleCode;
	}

	public String getAuthCode() {
		return authCode;
	}

	public void setAuthCode(String authCode) {
		this.authCode = authCode;
	}
	
	public static RoleAuth newInstance(String roleCode, String authCode) {
		return new RoleAuth(roleCode, authCode);
	}

}
