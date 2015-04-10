package com.profit.laji.oss.dao;

import java.io.Serializable;
import java.util.List;

import org.apache.commons.lang.Validate;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.support.SqlSessionDaoSupport;
import org.springframework.beans.factory.annotation.Autowired;

import com.profit.laji.entity.exception.DatabaseException;
import com.profit.laji.lang.util.ReflectUtils;

public class DaoSupport<T> extends SqlSessionDaoSupport {
	
	private int BTACH_SIZE = 5000;
	
	private Class<T> cls;
	
	public DaoSupport() {
		Class<?>[] classes = ReflectUtils.genericsTypes(getClass());
		if (classes != null && classes.length > 0) {
			this.cls = (Class<T>) classes[0];
		}
	}

	@Autowired
	public void setSqlSessionFactory(SqlSessionFactory sqlSessionFactory) {
		Validate.notNull(sqlSessionFactory, "sqlSessionFactory is required,please config it in spring xml");
		super.setSqlSessionFactory(sqlSessionFactory);
	}

	/**
	 * get the result by id
	 * 
	 * @param id
	 * @return
	 */
	public T get(String id) {
		Validate.notNull(id, "id must not be null");
		try {
			return (T)getSqlSession().selectOne(getNamespace().concat(".getById"), id);
		} catch (Exception e) {
			throw new DatabaseException(e.getMessage());
		}
	}
	
	public T get(T entity){
		Validate.notNull(entity, "id must not be null");
		try {
			return (T)getSqlSession().selectOne(getNamespace().concat(".get"), entity);
		} catch (Exception e) {
			throw new DatabaseException(e.getMessage());
		}
	}

	/**
	 * insert the entity to database,if exit,will be exception
	 * 
	 * @param entity
	 * @return
	 */
	public int insert(T entity) {
		Validate.notNull(entity, "entity must not be null");
		try {
			return getSqlSession().insert(getNamespace().concat(".insert"), entity);
		} catch (Exception e) {
			throw new DatabaseException(e.getMessage());
		}
	}

	/**
	 * update the entity to database
	 * 
	 * @param entity
	 * @return
	 */
	public int update(T entity) {
		Validate.notNull(entity, "entity must not be null");
		try {
			return getSqlSession().update(getNamespace().concat(".update"), entity);
		} catch (Exception e) {
			throw new DatabaseException(e.getMessage());
		}
	}
	
	/**
	 * 
	 * @param entity
	 * @return
	 */
	public int delete(T entity){
		Validate.notNull(entity,"entity must not be null");
		return getSqlSession().delete(getNamespace().concat(".delete"), entity);
	}
	
	protected String getNamespace() {
		return cls.getName();
	}
	
	protected int getSize() {
		return BTACH_SIZE;
	}
	
}
