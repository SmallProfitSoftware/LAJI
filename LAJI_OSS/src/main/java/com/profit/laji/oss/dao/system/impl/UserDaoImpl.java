package com.profit.laji.oss.dao.system.impl;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.profit.laji.entity.system.User;
import com.profit.laji.oss.dao.DaoSupport;
import com.profit.laji.oss.dao.system.UserDao;

/**
 * 用户持久层实现
 * @author heyang
 * 2015-03-25
 */
@Repository
public class UserDaoImpl extends DaoSupport<User> implements UserDao {

	@Override
	public List<User> getAll() {
		return getSqlSession().selectList(getNamespace().concat(".getAll"));
	}

	@Override
	public List<User> queryList(Map<String, String> params) {
		return getSqlSession().selectList(getNamespace().concat(".queryList"), params);
	}

	@Override
	public int queryCount(Map<String, String> params) {
		return getSqlSession().selectOne(getNamespace().concat(".queryCount"), params);
	}

	@Override
	public int save(User user) {
		return insert(user);
	}

	@Override
	public int delete(String userId) {
		return getSqlSession().delete(getNamespace().concat(".deleteById"), userId);
	}

	@Override
	public int delete(Map<String, String> params) {
		return getSqlSession().delete(getNamespace().concat(".delete"), params);
	}

	@Override
	public int lock(User user) {
		return getSqlSession().update(getNamespace().concat(".lock"), user);
	}

	@Override
	public int updateLastLogin(User user) {
		return getSqlSession().update(getNamespace().concat(".updateLastLogin"), user);
	}

	@Override
	public int updatePwd(User user) {
		return getSqlSession().update(getNamespace().concat(".updatePwd"), user);
	}

}
