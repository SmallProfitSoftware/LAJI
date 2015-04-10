package com.profit.laji.oss.controller.system;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.profit.laji.entity.Result;
import com.profit.laji.entity.system.Role;
import com.profit.laji.lang.annotation.AuthAnnotation;
import com.profit.laji.lang.constant.CodeConstant;
import com.profit.laji.lang.constant.MsgConstant;
import com.profit.laji.lang.util.StringUtils;
import com.profit.laji.lang.validator.annotation.NotNull;
import com.profit.laji.lang.validator.annotation.ValidatorRule;
import com.profit.laji.oss.controller.ControllerSupport;

/**
 * 角色控制类
 * @author heyang
 * 2015-03-31
 */
@Controller
@SuppressWarnings("all")
@RequestMapping("/views/system")
public class RoleController extends ControllerSupport {
	
    /** 
     * 角色列表
     */  
    @RequestMapping(value="/role_list.json", method = RequestMethod.POST)
    @ValidatorRule(notNull = @NotNull(names={"page", "pageSize"}))
    @AuthAnnotation(module="角色管理", act=MsgConstant.LIST, str="d_role")
    @ResponseBody
    public Object list(HttpServletRequest request, 
    						 HttpServletResponse response, 
    						 @RequestParam(value="page") int pageNo,
    						 @RequestParam(value="pageSize") int pageSize,
    						 @RequestParam(value="dir", required=false) String dir,
    						 @RequestParam(value="sort", required=false) String sort,
    						 Model model) throws Exception{  
    	String orderBy = "id asc";
		if (!StringUtils.isEmpty(dir))
			orderBy = dir + " " + sort;
    	Map<String, String> params = new HashMap<String, String>();
    	params.put("orderBy", orderBy);
    	params.put("bNo", ((pageNo - 1) * pageSize) + "");
		params.put("eNo", pageSize + "");
    	return roleService.getResults(params);
    }
    
    /** 
     * 添加角色
     */  
    @RequestMapping(value="/role_add.json", method = RequestMethod.POST)
    @AuthAnnotation(module="角色管理", act=MsgConstant.ROLE + MsgConstant.ADD, str="f_role_add")
    @ResponseBody
    public Object add(@ModelAttribute("role") Role role,
    					HttpServletRequest request, 
    						 HttpServletResponse response, 
    						 Model model) throws Exception{  
    	roleService.add(role);
    	return Result.newInstance(CodeConstant.CODE_200, role.getCode(), CodeConstant.CODE_SUC);
    }
    
    /** 
     * 获取角色集合
     */  
    @RequestMapping(value="/roles.json", method = RequestMethod.POST)
    @AuthAnnotation(module="角色管理", act=MsgConstant.ROLE + MsgConstant.GET, str="d_role")
    @ResponseBody
    public Object roles(HttpServletRequest request, 
    						 HttpServletResponse response, 
    						 Model model) throws Exception{  
    	return Result.newInstance((List<Role>) getSessionVal(request, ALL_ROLES));
    }
    
    /** 
     * 获取角色权限
     */  
    @RequestMapping(value="/role_auths.json", method = RequestMethod.POST)
    @AuthAnnotation(module="角色管理", act=MsgConstant.AUTH + MsgConstant.GET, str="d_role")
    @ResponseBody
    public Object roleAuths(@ModelAttribute("role") Role role,
    						HttpServletRequest request, 
    						 HttpServletResponse response, 
    						 Model model) throws Exception{
    	return Result.newInstance(roleService.queryRoleAuths(role.getCode()));
    }
    
    /** 
     * 设置角色权限
     */  
    @RequestMapping(value="/role_auth_setting.json", method = RequestMethod.POST)
    @AuthAnnotation(module="角色管理", act=MsgConstant.AUTH + MsgConstant.SETTING, str="f_role_auth_setting")
    @ResponseBody
    public Object authSetting(@ModelAttribute("role") String role,
    						@ModelAttribute("codes") String codes,
    						HttpServletRequest request, 
    						 HttpServletResponse response, 
    						 Model model) throws Exception{
    	roleService.updateRoleAuths(role, codes);
    	return Result.newInstance(CodeConstant.CODE_200, CodeConstant.CODE_SUC);
    }
    
}
