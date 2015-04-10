package com.profit.laji.oss.service;

import java.util.Map;

import com.profit.laji.entity.Result;

/**
 * 接口支撑类
 * @author heyang
 * 2015-03-30
 * @param <T>
 */
public interface ServiceSupport<T> {

	/**
	 * 获取结果集
	 * @param params
	 * @return
	 */
	public Result<T> getResults(Map<String, String> params);
	
	/**
	 * 新增
	 * @param t
	 * @return
	 */
	public int add(T t);
	
	/**
	 * 获取
	 * @param id
	 * @return
	 */
	public T get(T t);
	
	/**
	 * 修改
	 * @param t
	 * @return
	 */
	public int update(T t);
	
	/**
	 * 删除
	 * @param id
	 * @return
	 */
	public int delete(String id);
}
