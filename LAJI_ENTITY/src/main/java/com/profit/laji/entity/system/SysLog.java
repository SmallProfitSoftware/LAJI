package com.profit.laji.entity.system;

import java.io.Serializable;

/**
 * 系统日志
 * @author heyang
 * 2015-03-25
 */
public class SysLog implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3248445064836559996L;

	/**
	 * 自增长ID
	 */
	private String id;

	/**
	 * 操作目录
	 */
	private String module;
	
	/**
	 * 操作行为
	 */
	private String act;
	
	/**
	 * ip地址
	 */
	private String ip;
	
	/**
	 * 状态(success/error)
	 */
	private String status = "success";
	
	/**
	 * 日志信息
	 */
	private String message;
	
	/**
	 * 操作人
	 */
	private String operator;
	
	/**
	 * 操作时间
	 */
	private String operateDate;
	
	private SysLog() {};
	
	private SysLog(String module, String act, String ip, String status, String message, String operator) {
		this.module = module;
		this.act = act;
		this.ip = ip;
		this.status = status;
		this.message = message;
		this.operator = operator;
	}
	
	private SysLog(String module, String act, String ip, String status, String message, String operator, String operateDate) {
		this.module = module;
		this.act = act;
		this.ip = ip;
		this.status = status;
		this.message = message;
		this.operator = operator;
		this.operateDate = operateDate;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getOperator() {
		return operator;
	}

	public void setOperator(String operator) {
		this.operator = operator;
	}

	public String getModule() {
		return module;
	}

	public void setModule(String module) {
		this.module = module;
	}

	public String getAct() {
		return act;
	}

	public void setAct(String act) {
		this.act = act;
	}

	public String getIp() {
		return ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String getOperateDate() {
		return operateDate;
	}

	public void setOperateDate(String operateDate) {
		this.operateDate = operateDate;
	}
	
	public static SysLog newInstance(String module, String act, String ip, String status, String message, String operator) {
		return new SysLog(module, act, ip, status, message, operator);
	}
	
	public static SysLog newInstance(String module, String act, String ip, String status, String message, String operator, String operateDate) {
		return new SysLog(module, act, ip, status, message, operator, operateDate);
	}
	
}
