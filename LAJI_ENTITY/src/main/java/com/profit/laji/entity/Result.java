package com.profit.laji.entity;

import java.io.Serializable;

/**
 * 结果集
 * @author heyang
 * 2015-01-28
 */
public class Result<T> implements Serializable {
	
	private static final long serialVersionUID = 1L;

	/**
	 * 状态码
	 */
	private int retCode = 200;
	
	/**
	 * 状态(success,error)
	 */
	private String status = "success";
	
	/**
	 * 提示信息
	 */
	private String message;
	
	/**
	 * 结果集
	 */
	private T results;
	
	/**
	 * 返回总条数
	 */
	private int count;
	
	private Result() {}
	
	private Result(int retCode, String status) {
		this.retCode = retCode;
		this.status = status;
	}
	
	private Result(int retCode, String message, String status) {
		this.retCode = retCode;
		this.message = message;
		this.status = status;
	}
	
	private Result(T results, int count) {
		this.results = results;
		this.count = count;
	}
	
	private Result(T results) {
		this.results = results;
	}
	
	public int getRetCode() {
		return retCode;
	}

	public void setRetCode(int retCode) {
		this.retCode = retCode;
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

	public T getResults() {
		return results;
	}

	public void setResults(T results) {
		this.results = results;
	}

	public int getCount() {
		return count;
	}

	public void setCount(int count) {
		this.count = count;
	}
	
	public static Result newInstance(int retCode, String status) {
		return new Result(retCode, status);
	}

	public static Result newInstance(int retCode, String message, String status) {
		return new Result(retCode, message, status);
	}
	
	public static Result newInstance(Object results, int count) {
		return new Result(results, count);
	}
	
	public static Result newInstance(Object results) {
		return new Result(results);
	}
	
}
