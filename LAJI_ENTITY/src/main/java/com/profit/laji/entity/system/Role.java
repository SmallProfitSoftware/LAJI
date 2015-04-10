package com.profit.laji.entity.system;

import java.io.Serializable;
import java.util.List;

/**
 * 角色实体
 * @author heyang
 * 2015-02-12
 */
public class Role implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5605744895600741355L;

	/**
	 * 自增长ID
	 */
	private String id;
	
	/**
	 * 角色编码(系统自动生成,以R开头，如：R001,R002...)
	 */
	private String code;
	
	/**
	 * 角色名称
	 */
	private String name;

	/**
	 * 角色下的帐号集合
	 */
	private List<User> users;
	
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public List<User> getUsers() {
		return users;
	}

	public void setUsers(List<User> users) {
		this.users = users;
	}
	
}
