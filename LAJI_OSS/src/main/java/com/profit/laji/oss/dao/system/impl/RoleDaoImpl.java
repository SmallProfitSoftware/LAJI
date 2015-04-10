package com.profit.laji.oss.dao.system.impl;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.ExecutorType;
import org.apache.ibatis.session.SqlSession;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.profit.laji.entity.system.Role;
import com.profit.laji.entity.system.RoleAuth;
import com.profit.laji.oss.dao.DaoSupport;
import com.profit.laji.oss.dao.system.RoleDao;

/**
 * 角色持久层实现
 * @author heyang
 * 2015-03-31
 */
@Repository
public class RoleDaoImpl extends DaoSupport<Role> implements RoleDao {
	
	@Autowired
	private SqlSessionTemplate sqlSessionTemplate;
	
	private SqlSession batchSession;
	
	@Override
	public List<Role> getAll() {
		return getSqlSession().selectList(getNamespace().concat(".getAll"));
	}

	@Override
	public List<Role> queryList(Map<String, String> params) {
		return getSqlSession().selectList(getNamespace().concat(".queryList"), params);
	}

	@Override
	public int queryCount(Map<String, String> params) {
		return getSqlSession().selectOne(getNamespace().concat(".queryCount"), params);
	}
	
	@Override
	public List<RoleAuth> queryRoleAuths(String roleCode) {
		return getSqlSession().selectList(getNamespace().concat(".queryRoleAuths"), roleCode);
	}

	@Override
	public int save(Role role) {
		return insert(role);
	}

	@Override
	public String maxCode() {
		return getSqlSession().selectOne(getNamespace().concat(".maxCode"));
	}

	@Override
	public int deleteRA(String role) {
		return getSqlSession().delete(getNamespace().concat(".deleteRA"), role);
	}

	@Override
	public void add(List<RoleAuth> auths) {
		batchSession = sqlSessionTemplate.getSqlSessionFactory().openSession(ExecutorType.BATCH, false);
        for (int i =0, j = auths.size(); i < j; i++) { 
        	batchSession.update(getNamespace().concat(".add"), auths.get(i));
            if ((i + 1) % getSize() == 0) 
            	batchSession.flushStatements(); 
        }  
        batchSession.flushStatements();  
        batchSession.close();
	}

}
