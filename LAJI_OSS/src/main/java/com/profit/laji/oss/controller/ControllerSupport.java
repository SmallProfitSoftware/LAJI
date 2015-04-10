package com.profit.laji.oss.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.profit.laji.entity.exception.BusinessException;
import com.profit.laji.entity.system.Auth;
import com.profit.laji.entity.system.Role;
import com.profit.laji.entity.system.User;
import com.profit.laji.lang.constant.MsgConstant;
import com.profit.laji.lang.util.StringUtils;
import com.profit.laji.oss.service.system.AuthService;
import com.profit.laji.oss.service.system.RoleService;
import com.profit.laji.oss.service.system.SysLogService;
import com.profit.laji.oss.service.system.UserService;

/**
 * 控制层基类
 * @author heyang
 * 2015-03-25
 */
public class ControllerSupport {

	public static Logger LOG = LoggerFactory.getLogger(ControllerSupport.class);
	
	@Autowired
	public RoleService roleService;
	
	@Autowired
	public UserService userService;
	
	@Autowired
	public AuthService authService;
	
	@Autowired
	public SysLogService sysLogService;
	
	public static final String UPLOAD_DIR = "upload/doc";
	
	public static final String SUPERROLE = "R001";
	
	public static final String SUPERADMIN = "superadmin";
	
	public static final String USER_ALL_AUTHS = "user_all_auths";
	
	public static final String USER_DIR_AUTHS = "user_dir_auths";
	
	public static final String USER_MODULE_AUTHS = "user_module_auths";
	
	public static final String USER_FUNC_AUTHS = "user_func_auths";
	
	public static final String USER_AUTHS_MAP = "user_auths_map";
	
	public static final String AUTH_CODE_MAP = "auths_code_map";
	
	public static final String ALL_ROLES = "all_roles";
	
	public static final String ALL_AUTHS = "all_auths";
	
	public static final String DIR_AUTHS = "dir_auths";
	
	public static final String MODULE_AUTHS = "module_auths";
	
	public static final String FUNC_AUTHS = "func_auths";
	
	public static final String AUTHS_MAP = "auths_map";
	
	public static final String ALL_ROLES_MAP = "all_roles_map";
	
	/**
	 * 是否超级角色
	 * @param code
	 * @return
	 */
	public boolean isSuperRole(String code) {
		return code.equals(SUPERROLE);
	}
	
	/**
	 * 当前会话用户
	 * @param request
	 * @return
	 */
	public User currUser(HttpServletRequest request) throws Exception {
		User user = (User) request.getSession().getAttribute("user");
		if (user == null) 
			throw new BusinessException(MsgConstant.EXCEPTION_NOLOGIN_SESSION_OVERDUE);
		return user;
	}
	
	/**
	 * 当前会话用户ID
	 * @param request
	 * @return
	 */
	public String currUserId(HttpServletRequest request) throws Exception {
		return currUser(request).getUserId();
	}
	
	/**
	 * 获取session
	 * @param request
	 * @return
	 */
	public HttpSession getSession(HttpServletRequest request) {
		return request.getSession();
	}
	
	/**
	 * 获取session信息
	 * @param request
	 * @param key
	 * @return
	 */
	public Object getSessionVal(HttpServletRequest request, String key) {
		HttpSession session = getSession(request);
		return session.getAttribute(key);
	}
	
	/**
	 * 缓存用户权限
	 * @param auths
	 * @param request
	 */
	public void cacheAuths(List<Auth> auths, HttpServletRequest request, String[] keyArr) {
		Map<String, Boolean> authMap = new HashMap<String, Boolean>();
		Map<String, Auth> codeMap = new HashMap<String, Auth>();
		List<Auth> dirAuths = Lists.newArrayList();
		List<Auth> moduleAuths = Lists.newArrayList();
		List<Auth> funcAuths = Lists.newArrayList();
		for (Auth auth : auths) {
			authMap.put(auth.getStr(), true);
			codeMap.put(auth.getCode(), auth);
			if (isAuth(auth.getCode(), 2))
				dirAuths.add(auth);
			if (isAuth(auth.getCode(), 4))
				moduleAuths.add(auth);
			if (isAuth(auth.getCode(), 6))
				funcAuths.add(auth);
		}
		//添加模块子权限
		for (Auth auth : moduleAuths) {
			List<Auth> childrens = Lists.newArrayList();
			for (Auth f : funcAuths) {
				if (auth.getCode().equals(f.getCode().substring(0, 4))) {
					childrens.add(f);
				}
			}
			auth.setAuths(childrens);
		}
		//添加目录子权限
		for (Auth auth : dirAuths) {
			List<Auth> childrens = Lists.newArrayList();
			for (Auth m : moduleAuths) {
				if (auth.getCode().equals(m.getCode().substring(0, 2))) {
					childrens.add(m);
				}
			}
			auth.setAuths(childrens);
		}
		HttpSession session = getSession(request);
		session.setAttribute(keyArr[0], auths);
		session.setAttribute(keyArr[1], dirAuths);
		session.setAttribute(keyArr[2], moduleAuths);
		session.setAttribute(keyArr[3], funcAuths);
		session.setAttribute(keyArr[4], authMap);
		session.setAttribute(AUTH_CODE_MAP, codeMap);
	}
	
	/**
	 * 缓存角色
	 * @param roles
	 * @param request
	 */
	public void cacheRoles(List<Role> roles, HttpServletRequest request) {
		List<Role> allRoles = Lists.newArrayList();
		Map<String, Role> roleMap = new HashMap<String, Role>();
		for(Role role : roles) {
			roleMap.put(role.getCode(), role);
			if (isSuperRole(role.getCode()))
				continue;
			allRoles.add(role);
		}
		HttpSession session = getSession(request);
		session.setAttribute(ALL_ROLES, allRoles);
		session.setAttribute(ALL_ROLES_MAP, roleMap);
	}
	
	/**
	 * 转换权限集
	 * @param auths
	 * @return
	 */
	public List<Auth> convertAuths(List<Auth> auths, int length) {
		List<Auth> newAuths = Lists.newArrayList();
		for (Auth auth : auths) {
			if (isAuth(auth.getCode(), length))
				newAuths.add(auth);
		}
		return newAuths;
	}
	
	/**
	 * 是否符合权限
	 * @param code
	 * @param length
	 * @return
	 */
	public boolean isAuth(String code, int length) {
		return length == code.length();
	}
	
	/**
	 *  获取指定参数集合
	 * @param request
	 * @param nameArr
	 * @return
	 */
	public Map<String, String> getReqParams(HttpServletRequest request, String[] nameArr) {
		Map<String, String> params = new HashMap<String, String>();
		if (nameArr != null) {
			for (String name : nameArr) {
				String val = request.getParameter(name);
				if (!StringUtils.isEmpty(val))
					params.put(name, val);
			}
		}
		return params;
	}
	
}
