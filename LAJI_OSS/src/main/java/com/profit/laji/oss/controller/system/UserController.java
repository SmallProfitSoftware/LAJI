package com.profit.laji.oss.controller.system;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.shiro.SecurityUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.google.common.collect.Lists;
import com.profit.laji.entity.Result;
import com.profit.laji.entity.system.Auth;
import com.profit.laji.entity.system.Role;
import com.profit.laji.entity.system.RoleAuth;
import com.profit.laji.entity.system.User;
import com.profit.laji.lang.annotation.AuthAnnotation;
import com.profit.laji.lang.constant.CodeConstant;
import com.profit.laji.lang.constant.MsgConstant;
import com.profit.laji.lang.util.HttpUtils;
import com.profit.laji.lang.util.MD5Encoder;
import com.profit.laji.lang.util.RandomUtils;
import com.profit.laji.lang.util.StringUtils;
import com.profit.laji.lang.validator.annotation.NotNull;
import com.profit.laji.lang.validator.annotation.ValidatorRule;
import com.profit.laji.oss.controller.ControllerSupport;

/**
 * 账号控制类
 * @author heyang
 * 2015-02-12
 */
@Controller
@SuppressWarnings("all")
@RequestMapping("/views/system")
public class UserController extends ControllerSupport {
	
	 /** 
     * 用户登录 
     */  
    @RequestMapping(value="/login", method = RequestMethod.POST)
    @ValidatorRule(notNull = @NotNull(names={"userId", "userPwd"}))
    @AuthAnnotation(module="登录页面", act=MsgConstant.LOGIN, check=false)
    @ResponseBody
    public Object login(@ModelAttribute("user") User user, 
    						 HttpServletRequest request, 
    						 HttpServletResponse response, 
    						 Model model) throws Exception{  
    	String ip = HttpUtils.getClientIP(request);
    	user.setLastLoginIp(ip);
    	LOG.debug("用户[" + user.getUserId() + "]在[" + ip + "处进行登陆操作!");
    	boolean isLogin = userService.login(user);
    	if (isLogin) {
    		LOG.debug("用户[" + user.getUserId() + "]登录认证成功!");
    		//缓存所有权限
    		List<Auth> allAuths = authService.getAll();
    		cacheAuths(allAuths, request, new String[]{ALL_AUTHS, DIR_AUTHS, MODULE_AUTHS, FUNC_AUTHS, AUTHS_MAP});
    		//缓存所有角色
    		List<Role> roles = roleService.getAll();
    		cacheRoles(roles, request);
    		
    		List<Auth> auths = null;
    		if (isSuperRole(currUser(request).getRole())) {
    			auths = allAuths;
    		} else {
    			//获取角色权限
    			List<RoleAuth> roleAuths = roleService.queryRoleAuths(currUser(request).getRole());
    			auths = Lists.newArrayList();
    			Map<String, Auth> codeMap = (Map<String, Auth>) getSessionVal(request, AUTH_CODE_MAP);
    			for (RoleAuth roleAuth : roleAuths) {
    				auths.add(codeMap.get(roleAuth.getAuthCode()));
    			}
    		}
    		//缓存用户权限
    		cacheAuths(auths, request, new String[]{USER_ALL_AUTHS, USER_DIR_AUTHS, USER_MODULE_AUTHS, USER_FUNC_AUTHS, USER_AUTHS_MAP});
    		return Result.newInstance(CodeConstant.CODE_200, CodeConstant.CODE_SUC);
    	}
   		LOG.debug("用户[" + user.getUserId() + "]登录认证失败!");
    	return Result.newInstance(CodeConstant.CODE_500, null, CodeConstant.CODE_FAIL);
    }
    
    /** 
     * 用户退出 
     */  
    @RequestMapping(value="/logout", method = RequestMethod.GET)
    @AuthAnnotation(module="首页", act=MsgConstant.EXIT, check=false)
    public ModelAndView logout(HttpServletRequest request) throws Exception{
    	LOG.debug("账户[" + currUserId(request) + "]退出！");
    	request.getSession().removeAttribute("user");
        SecurityUtils.getSubject().logout();
        return new ModelAndView("redirect:/login.html");
    }  
    
    /**
     * 首页
     * @param user
     * @param request
     * @param response
     * @param model
     * @return
     * @throws Exception
     */
    @RequestMapping(value="/main.html", method = RequestMethod.GET)
    public ModelAndView index(HttpServletRequest request, 
			 HttpServletResponse response, 
			 Model model) throws Exception{  
    	return new ModelAndView("/main");
    }
    
    /** 
     * 用户列表
     */  
    @RequestMapping(value="/user_list.json", method = RequestMethod.POST)
    @ValidatorRule(notNull = @NotNull(names={"page", "pageSize"}))
    @AuthAnnotation(module="用户管理", act=MsgConstant.LIST, str="d_user")
    @ResponseBody
    public Object list(HttpServletRequest request, 
    						 HttpServletResponse response, 
    						 @RequestParam(value="page") int pageNo,
    						 @RequestParam(value="pageSize") int pageSize,
    						 @RequestParam(value="dir", required=false) String dir,
    						 @RequestParam(value="sort", required=false) String sort,
    						 Model model) throws Exception{  
    	String orderBy = "updateDate desc";
		if (!StringUtils.isEmpty(dir))
			orderBy = dir + " " + sort;
    	Map<String, String> params = getReqParams(request, new String[]{"role"});
    	params.put("orderBy", orderBy);
    	params.put("bNo", ((pageNo - 1) * pageSize) + "");
		params.put("eNo", pageSize + "");
		params.put(request.getParameter("userfield"), request.getParameter("content"));
		Result<User> results = (Result<User>) userService.getResults(params);
		List<User> users = (List<User>) results.getResults();
		if (StringUtils.isNull(users))
			return results;
		Map<String, Role> roleMap = (Map<String, Role>) getSessionVal(request, ALL_ROLES_MAP);
		for (User user : users) {
			user.setRoleName(roleMap.get(user.getRole()).getName());
		}
    	return results;
    }
    
    /** 
     * 添加帐号 
     */  
    @RequestMapping(value="/user_add.json", method = RequestMethod.POST)
    @AuthAnnotation(module="帐号管理", act=MsgConstant.USER + MsgConstant.ADD, str="f_user_add")
    @ResponseBody
    public Object add(@ModelAttribute("user") User user,
    					HttpServletRequest request, 
    						 HttpServletResponse response, 
    						 Model model) throws Exception{  
    	String pwd = RandomUtils.randomPwd(6);
    	user.setUserPwd(MD5Encoder.encode(pwd));
    	user.setCreater(currUserId(request));
    	user.setUpdater(currUserId(request));
    	userService.add(user);
    	return Result.newInstance(CodeConstant.CODE_200, pwd, CodeConstant.CODE_SUC);
    }
    
    /** 
     * 修改帐号
     */  
    @RequestMapping(value="/user_edit.json", method = RequestMethod.POST)
    @AuthAnnotation(module="帐号管理", act=MsgConstant.USER + MsgConstant.UPDATE, str="f_user_edit")
    @ResponseBody
    public Object edit(@ModelAttribute("user") User user,
    					HttpServletRequest request, 
    						 HttpServletResponse response, 
    						 Model model) throws Exception{ 
    	user.setUpdater(currUserId(request));
    	userService.update(user);
    	return Result.newInstance(CodeConstant.CODE_200, CodeConstant.CODE_SUC);
    }
    
    /** 
     * 锁定/解锁帐号
     */  
    @RequestMapping(value="/user_lock.json", method = RequestMethod.POST)
    @AuthAnnotation(module="帐号管理", act=MsgConstant.USER + MsgConstant.LOCK, str="f_user_lock")
    @ResponseBody
    public Object lock(@ModelAttribute("user") User user,
    					HttpServletRequest request, 
    						 HttpServletResponse response, 
    						 Model model) throws Exception{ 
    	user.setUpdater(currUserId(request));
    	userService.lock(user);
    	return Result.newInstance(CodeConstant.CODE_200, CodeConstant.CODE_SUC);
    }
    
    /** 
     * 修改密码
     */  
    @RequestMapping(value="/user_pwd_edit.json", method = RequestMethod.POST)
    @ValidatorRule(notNull = @NotNull(names={"newPwd"}))
    @AuthAnnotation(module="首页", act=MsgConstant.PASSWORD + MsgConstant.UPDATE, check=false)
    @ResponseBody
    public Object updatePwd(@ModelAttribute("user") User user,
    					@RequestParam(value="newPwd") String newPwd,
    					HttpServletRequest request, 
    						 HttpServletResponse response, 
    						 Model model) throws Exception{ 
    	user.setUserId(currUserId(request));
    	//校验
    	boolean bool = userService.checkPwd(user);
    	if (bool) {
        	user.setUpdater(currUserId(request));
        	user.setUserPwd(MD5Encoder.encode(newPwd));
        	userService.updatePwd(user);
        	return Result.newInstance(CodeConstant.CODE_200, CodeConstant.CODE_SUC);
    	}
    	return Result.newInstance(CodeConstant.CODE_500, CodeConstant.CODE_FAIL);
    }
    
    /** 
     * 重置密码
     */  
    @RequestMapping(value="/user_pwd_reset.json", method = RequestMethod.POST)
    @AuthAnnotation(module="帐号管理", act=MsgConstant.PASSWORD + MsgConstant.RESET, str="f_user_pwd_reset")
    @ResponseBody
    public Object resetPwd(@ModelAttribute("user") User user,
    					HttpServletRequest request, 
    						 HttpServletResponse response, 
    						 Model model) throws Exception{
		String pwd = RandomUtils.randomPwd(6);
    	user.setUpdater(currUserId(request));
    	user.setUserPwd(MD5Encoder.encode(pwd));
    	userService.updatePwd(user);
    	return Result.newInstance(CodeConstant.CODE_200, pwd, CodeConstant.CODE_SUC);
    }
    
    /** 
     * 校验密码
     */  
    @RequestMapping(value="/user_pwd_check.json", method = RequestMethod.POST)
    @AuthAnnotation(module="首页", act=MsgConstant.PASSWORD + MsgConstant.CHECK, check=false)
    @ResponseBody
    public Object check(@ModelAttribute("user") User user,
    					HttpServletRequest request, 
    						 HttpServletResponse response, 
    						 Model model) throws Exception{
    	user.setUserId(currUserId(request));
    	userService.checkPwd(user);
    	return Result.newInstance(CodeConstant.CODE_200, CodeConstant.CODE_SUC);
    }
    
}
