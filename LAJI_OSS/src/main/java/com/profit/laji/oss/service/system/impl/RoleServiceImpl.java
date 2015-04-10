package com.profit.laji.oss.service.system.impl;


import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.common.collect.Lists;
import com.profit.laji.entity.Result;
import com.profit.laji.entity.exception.BusinessException;
import com.profit.laji.entity.system.Role;
import com.profit.laji.entity.system.RoleAuth;
import com.profit.laji.entity.system.User;
import com.profit.laji.lang.constant.CodeConstant;
import com.profit.laji.lang.constant.MsgConstant;
import com.profit.laji.lang.util.CodeUtils;
import com.profit.laji.lang.util.StringUtils;
import com.profit.laji.oss.dao.system.RoleDao;
import com.profit.laji.oss.dao.system.UserDao;
import com.profit.laji.oss.service.system.RoleService;

/**
 * 角色业务接口实现
 * 
 * @author heyang 2015-03-31
 */
@Service
public class RoleServiceImpl implements RoleService {

	public static Logger LOG = LoggerFactory.getLogger(RoleService.class);
	
	@Autowired
	private RoleDao roleDao;
	
	@Autowired
	private UserDao userDao;
	
	@Override
	public Result<Role> getResults(Map<String, String> params) {
		int count = roleDao.queryCount(params);
		if (count == 0)
			return Result.newInstance(null, 0);
		List<Role> roles = roleDao.queryList(params);
		if (!StringUtils.isListNull(roles)) {
			List<User> users = userDao.getAll();
			for (Role r : roles) {
				List<User> us = Lists.newArrayList();
				for (User u : users) {
					if (r.getCode().equals(u.getRole()))
						us.add(u);
				}
				r.setUsers(us);
			}
		}
		return Result.newInstance(roles, count);
	}

	@Override
	public int add(Role role) {
		if (isExist(role))
			throw new BusinessException(MsgConstant.EXCEPTION_NAME_EXIST);
		role.setCode(maxCode());
		return roleDao.save(role);
	}
	
	@Override
	public int update(Role role) {
		if (isExist(role))
			throw new BusinessException(MsgConstant.EXCEPTION_NAME_EXIST);
		return roleDao.update(role);
	}

	@Override
	public Role get(Role t) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int delete(String id) {
		// TODO Auto-generated method stub
		return 0;
	}

	/**
	 * 是否存在
	 * @param role
	 * @return
	 */
	private boolean isExist(Role role) {
		Role r = roleDao.get(role);
		if (!StringUtils.isEmpty(role.getId())) { //修改
			return !role.getId().equals(r.getId());
		}
		return !StringUtils.isNull(r);
	}
	
	/**
	 * 最大编码
	 * @return
	 */
	private String maxCode() {
		String maxCode = roleDao.maxCode();
		if (StringUtils.isEmpty(maxCode))
			return CodeConstant.ROLE_CODE_PREFIX + "001";
		return CodeConstant.ROLE_CODE_PREFIX + CodeUtils.nextCode(maxCode.substring(1, maxCode.length()), "#000");
	}

	@Override
	public List<Role> getAll() {
		return roleDao.getAll();
	}

	@Override
	public List<RoleAuth> queryRoleAuths(String roleCode) {
		return roleDao.queryRoleAuths(roleCode);
	}

	@Override
	public int updateRoleAuths(String role, String codes) {
		List<RoleAuth> auths = Lists.newArrayList();
		if (!StringUtils.isEmpty(codes)) {
			String[] codeArr = codes.split(",");
			for (String code : codeArr) {
				auths.add(RoleAuth.newInstance(role, code)); 
			}
		}
		//删除角色旧权限
		roleDao.deleteRA(role);
		//批量增加权限
		roleDao.add(auths);
		return 0;
	}

}
