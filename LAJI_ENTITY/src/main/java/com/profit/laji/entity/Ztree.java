package com.profit.laji.entity;

import java.io.Serializable;

/**
 * ztree实体
 * @author heyang
 * 2015-03-26
 */
public class Ztree implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7604186596046160084L;

	/**
	 * id
	 */
	private String id;
	
	/**
	 * 父节点id
	 */
	private String pId;
	
	/**
	 * 名称
	 */
	private String name;
	
	/**
	 * 字符串
	 */
	private String str;
	
	/**
	 * url
	 */
	private String url;
	
	private Ztree() {}
	
	private Ztree(String id, String pId, String name, String str, String url) {
		this.id = id;
		this.pId = pId;
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

	public String getpId() {
		return pId;
	}

	public void setpId(String pId) {
		this.pId = pId;
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

	public static Ztree newInstance(String id, String pId, String name, String str, String url) {
		return new Ztree(id, pId, name, str, url);
	}
	
}
