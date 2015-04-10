package com.profit.laji.entity.system;

import java.io.Serializable;
import java.util.List;

/**
 * 权限实体
 * @author heyang
 * 2015-02-12
 */
public class Auth implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -404526878693102026L;

	/**
	 * 自增长ID
	 */
	private String id;

	/**
	 * 权限编码
	 */
	private String code;
	
	/**
	 * 权限名称
	 */
	private String name;
	
	/**
	 * 权限字符串
	 */
	private String str;
	
	/**
	 * 权限URL
	 */
	private String url;
	
	/**
	 * 子权限
	 */
	private List<Auth> auths;
	
	private Auth(){}
	
	private Auth(String code, String name, String str, String url) {
		this.code = code;
		this.name = name;
		this.str = str;
		this.url = url;
	}

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

	public String getStr() {
		return str;
	}

	public void setStr(String str) {
		this.str = str;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public List<Auth> getAuths() {
		return auths;
	}

	public void setAuths(List<Auth> auths) {
		this.auths = auths;
	}
	
	public static Auth newInstance(String code, String name, String str, String url){
		return new Auth(code, name, str, url);
	}
	
}
