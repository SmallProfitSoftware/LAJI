package com.profit.laji.oss.service.system.impl;


import java.util.List;
import java.util.Map;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.profit.laji.entity.Result;
import com.profit.laji.entity.exception.BusinessException;
import com.profit.laji.entity.exception.ParameterException;
import com.profit.laji.entity.system.Auth;
import com.profit.laji.entity.system.Role;
import com.profit.laji.entity.system.User;
import com.profit.laji.lang.constant.MsgConstant;
import com.profit.laji.lang.util.MD5Encoder;
import com.profit.laji.lang.util.StringUtils;
import com.profit.laji.oss.dao.system.UserDao;
import com.profit.laji.oss.service.system.UserService;

/**
 * 用户业务接口实现
 * 
 * @author heyang 2015-03-05
 */
@Service
public class UserServiceImpl implements UserService {

	public static Logger LOG = LoggerFactory.getLogger(UserService.class);
	
	@Autowired
	private UserDao userDao;

	@Override
	public boolean login(User user) {
		String md5Pwd = MD5Encoder.encode(user.getUserPwd());
		UsernamePasswordToken token = new UsernamePasswordToken(
				user.getUserId(), md5Pwd);
		Subject currentUser = SecurityUtils.getSubject();
		//登录认证
		currentUser.login(token);
		//是否认证成功
		if(currentUser.isAuthenticated()){
			token.setRememberMe(true);
			//更新用户登录信息
			userDao.updateLastLogin(user);
			return true;
		}
		token.clear();  
		return false;
	}

	@Override
	public Result<User> getResults(Map<String, String> params) {
		int count = userDao.queryCount(params);
		if (count == 0)
			return Result.newInstance(null, 0);
		List<User> list = userDao.queryList(params);
		return Result.newInstance(list, count);
	}

	@Override
	public int add(User t) {
		//校验帐号ID是否合法:只能是数字和字母
		if (!t.getUserId().matches("^[a-zA-Z0-9]+$"))
			throw new BusinessException(MsgConstant.EXCEPTION_USERID_FORMAT_ERROR);
		if (isExist(t))
			throw new BusinessException(MsgConstant.EXCEPTION_USERID_EXIST);
		return userDao.save(t);
	}

	@Override
	public User get(String userId) {
		return userDao.get(userId);
	}

	@Override
	public int delete(String id) {
		return 0;
	}

	@Override
	public int update(User t) {
		return userDao.update(t);
	}

	@Override
	public List<User> getAll() {
		return userDao.getAll();
	}
	
	private boolean isExist(User user) {
		if (StringUtils.isEmpty(user.getId())) {
			User u = userDao.get(user.getUserId());
			return !StringUtils.isNull(u);
		}
		return false;
	}

	@Override
	public User get(User t) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int lock(User user) {
		return userDao.lock(user);
	}

	@Override
	public int updatePwd(User user) {
		return userDao.updatePwd(user);
	}

	@Override
	public boolean checkPwd(User user) {
		String pwd = user.getUserPwd();
		if (StringUtils.isEmpty(pwd))
			throw new ParameterException(MsgConstant.EXCEPTION_PASSWORD_NULL);
		String md5Pwd = MD5Encoder.encode(pwd);
		User u = userDao.get(user.getUserId());
		if (StringUtils.isNull(u))
			throw new ParameterException(MsgConstant.EXCEPTION_USER_NOEXIST);
		if (!md5Pwd.equals(u.getUserPwd()))
			throw new BusinessException(MsgConstant.EXCEPTION_PASSWORD_NOTSAME);
		return true;
	}

}
