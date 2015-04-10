package com.profit.laji.entity.system;

import java.io.Serializable;

/**
 * 用户实体
 * @author heyang
 * 2015-02-12
 */
public class User implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1955356905816347882L;

	/**
	 * 自增长ID
	 */
	private String id;
	
	/**
	 * 角色编码
	 */
	private String role;
	
	/**
	 * 角色名称
	 */
	private String roleName;
	
	/**
	 * 账号ID
	 */
	private String userId;

	/**
	 * 账号密码
	 */
	private String userPwd;
	
	/**
	 * 账号姓名
	 */
	private String userName;
	
	/**
	 * 锁定状态(1-锁定)
	 */
	private int isLock;
	
	/**
	 * 登录次数
	 */
	private int loginCount;
	
	/**
	 * 最后登录IP
	 */
	private String lastLoginIp;
	
	/**
	 * 最后登录时间
	 */
	private String lastLoginDate;
	
	/**
	 * 创建时间
	 */
	private String createDate;
	
	/**
	 * 创建人
	 */
	private String creater;
	
	/**
	 * 更新时间
	 */
	private String updateDate;
	
	/**
	 * 更新人
	 */
	private String updater;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getUserPwd() {
		return userPwd;
	}

	public void setUserPwd(String userPwd) {
		this.userPwd = userPwd;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public int getIsLock() {
		return isLock;
	}

	public void setIsLock(int isLock) {
		this.isLock = isLock;
	}

	public int getLoginCount() {
		return loginCount;
	}

	public void setLoginCount(int loginCount) {
		this.loginCount = loginCount;
	}

	public String getLastLoginIp() {
		return lastLoginIp;
	}

	public void setLastLoginIp(String lastLoginIp) {
		this.lastLoginIp = lastLoginIp;
	}

	public String getLastLoginDate() {
		return lastLoginDate;
	}

	public void setLastLoginDate(String lastLoginDate) {
		this.lastLoginDate = lastLoginDate;
	}

	public String getCreateDate() {
		return createDate;
	}

	public void setCreateDate(String createDate) {
		this.createDate = createDate;
	}

	public String getCreater() {
		return creater;
	}

	public void setCreater(String creater) {
		this.creater = creater;
	}

	public String getUpdateDate() {
		return updateDate;
	}

	public void setUpdateDate(String updateDate) {
		this.updateDate = updateDate;
	}

	public String getUpdater() {
		return updater;
	}

	public void setUpdater(String updater) {
		this.updater = updater;
	}

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}

	public String getRoleName() {
		return roleName;
	}

	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}
	
}
