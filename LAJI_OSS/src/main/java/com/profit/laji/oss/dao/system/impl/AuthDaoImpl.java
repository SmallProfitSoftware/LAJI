package com.profit.laji.oss.dao.system.impl;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.ExecutorType;
import org.apache.ibatis.session.SqlSession;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.profit.laji.entity.system.Auth;
import com.profit.laji.oss.dao.DaoSupport;
import com.profit.laji.oss.dao.system.AuthDao;
/**
 * 权限持久层实现
 * @author heyang
 * 2015-03-24
 */
@Repository
public class AuthDaoImpl extends DaoSupport<Auth> implements AuthDao {
	
	@Autowired
	private SqlSessionTemplate sqlSessionTemplate;
	
	private SqlSession batchSession;
	
	@Override
	public List<Auth> getAll() {
		return getSqlSession().selectList(getNamespace().concat(".getAll"));
	}

	@Override
	public int add(Auth auth) {
		return insert(auth);
	}

	@Override
	public Auth getExist(Auth auth) {
		return getSqlSession().selectOne(getNamespace().concat(".getExist"), auth);
	}

	@Override
	public String maxCode(Map<String, String> params) {
		return getSqlSession().selectOne(getNamespace().concat(".maxCode"), params);
	}

	@Override
	public int delete(String id) {
		return getSqlSession().delete(getNamespace().concat(".delByCode"), id);
	}

	@Override
	public List<Auth> queryList(Map<String, String> params) {
		return getSqlSession().selectList(getNamespace().concat(".queryList"), params);
	}

	@Override
	public int queryCount(Map<String, String> params) {
		return getSqlSession().selectOne(getNamespace().concat(".queryCount"), params);
	}

	@Override
	public List<Auth> getExists(Map<String, String> params) {
		return getSqlSession().selectList(getNamespace().concat(".getExists"), params);
	}

	@Override
	public void add(List<Auth> auths) {
		batchSession = sqlSessionTemplate.getSqlSessionFactory().openSession(ExecutorType.BATCH, false);
        for (int i =0, j = auths.size(); i < j; i++) { 
        	batchSession.update(getNamespace().concat(".insert"), auths.get(i));
            if ((i + 1) % getSize() == 0) 
            	batchSession.flushStatements(); 
        }  
        batchSession.flushStatements();  
        batchSession.close();
	}

}
