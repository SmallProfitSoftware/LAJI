package com.profit.laji.oss.controller.system;

import java.io.File;
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
import org.springframework.web.multipart.MultipartFile;

import com.google.common.collect.Lists;
import com.profit.laji.entity.Result;
import com.profit.laji.entity.Ztree;
import com.profit.laji.entity.system.Auth;
import com.profit.laji.lang.annotation.AuthAnnotation;
import com.profit.laji.lang.constant.CodeConstant;
import com.profit.laji.lang.constant.MsgConstant;
import com.profit.laji.lang.util.ExcelUtils;
import com.profit.laji.lang.util.FileUtils;
import com.profit.laji.lang.util.JsonUtils;
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
public class AuthController extends ControllerSupport {
	
	/** 
     * 目录树
     */  
    @RequestMapping(value="/dir_tree.json", method = RequestMethod.POST)
    @AuthAnnotation(module="目录管理", act=MsgConstant.TREE, str="d_dir")
    @ResponseBody
    public Object tree(HttpServletRequest request, 
    						 HttpServletResponse response, 
    						 Model model) throws Exception{  
    	return buildZtrees();
    }
    
    /** 
     * 权限树
     */  
    @RequestMapping(value="/auth_tree.json", method = RequestMethod.POST)
    @AuthAnnotation(module="权限管理", act=MsgConstant.TREE, str="d_auth")
    @ResponseBody
    public Object _tree(HttpServletRequest request, 
    						 HttpServletResponse response, 
    						 Model model) throws Exception{  
    	List<Ztree> ztrees = buildZtrees();
    	if (!StringUtils.isListNull(ztrees)) {
    		ztrees.add(0, Ztree.newInstance("0", "-1", "所有权限", "all_auths", ""));
    	}
    	return ztrees;
    }
    
    /** 
     * 权限列表
     */  
    @RequestMapping(value="/auth_list.json", method = RequestMethod.POST)
    @ValidatorRule(notNull = @NotNull(names={"page", "pageSize"}))
    @AuthAnnotation(module="权限管理", act=MsgConstant.TREE, str="d_auth")
    @ResponseBody
    public Object list(HttpServletRequest request, 
						 HttpServletResponse response, 
						 @RequestParam(value="page") int pageNo,
						 @RequestParam(value="pageSize") int pageSize,
						 @RequestParam(value="dir", required=false) String dir,
						 @RequestParam(value="sort", required=false) String sort,
						 Model model) throws Exception{  
    	String orderBy = " code asc";
		if (!StringUtils.isEmpty(dir))
			orderBy = dir + " " + sort;
    	Map<String, String> params = getReqParams(request, new String[]{"pcode"});
    	params.put("len", "6");
    	params.put("orderBy", orderBy);
    	params.put("bNo", ((pageNo - 1) * pageSize) + "");
		params.put("eNo", pageSize + "");
		params.put(request.getParameter("authfield"), request.getParameter("content"));
		return authService.getResults(params);
    }
    
    /** 
     * 删除目录 
     */  
    @RequestMapping(value="/auth_import.json", method = RequestMethod.POST)
    @ValidatorRule(notNull = @NotNull(names={"pid"}))
    @AuthAnnotation(module="权限管理", act=MsgConstant.AUTH + MsgConstant.IMPORT, str="f_auth_import")
    @ResponseBody
    public String _import(@RequestParam(value = "file", required = false) MultipartFile file,
    		@RequestParam(value="pid", required = false) String pid,
			HttpServletRequest request, HttpServletResponse response, Model model) throws Exception {
    	response.setContentType("text/html"); 
		response.setCharacterEncoding("UTF-8"); 
		String path = request.getSession().getServletContext().getRealPath(UPLOAD_DIR);
		String fileName = file.getOriginalFilename();
		//真实文件名称
		if (!ExcelUtils.isExcel(fileName))
			return JsonUtils.toJsonString(Result.newInstance(CodeConstant.CODE_500, MsgConstant.EXCEPTION_FILE_EXTNAME_ERROR, CodeConstant.CODE_FAIL));
		String randomName = FileUtils.rename(fileName);
		//将本地文件转存到服务器上
		File targetFile = FileUtils.transferTo(file, path, randomName);
		String message = authService._import(targetFile, pid, currUserId(request));
		boolean bool = targetFile.delete();
		if (!message.matches("[0-9]+"))
			return JsonUtils.toJsonString(Result.newInstance(CodeConstant.CODE_500, message, CodeConstant.CODE_FAIL));
    	return JsonUtils.toJsonString(Result.newInstance(CodeConstant.CODE_200, message, CodeConstant.CODE_SUC));
    }
    
    /** 
     * 添加目录 
     */  
    @RequestMapping(value="/dir_add.json", method = RequestMethod.POST)
    @AuthAnnotation(module="目录管理", act=MsgConstant.DIR + MsgConstant.ADD, str="f_dir_add")
    @ResponseBody
    public Object add(@ModelAttribute("auth") Auth auth,
    				  @RequestParam(value="pid", required=false) String pid,
    					HttpServletRequest request, 
    						 HttpServletResponse response, 
    						 Model model) throws Exception{  
    	authService.add(auth, pid);
    	return Result.newInstance(CodeConstant.CODE_200, auth.getCode(), CodeConstant.CODE_SUC);
    }
    
    /** 
     * 添加目录 
     */  
    @RequestMapping(value="/dir_edit.json", method = RequestMethod.POST)
    @AuthAnnotation(module="目录管理", act=MsgConstant.DIR + MsgConstant.UPDATE, str="f_dir_edit")
    @ResponseBody
    public Object edit(@ModelAttribute("auth") Auth auth,
    					HttpServletRequest request, 
    						 HttpServletResponse response, 
    						 Model model) throws Exception{  
    	authService.update(auth);
    	return Result.newInstance(CodeConstant.CODE_200, CodeConstant.CODE_SUC);
    }
    
    /** 
     * 删除目录 
     */  
    @RequestMapping(value="/dir_del.json", method = RequestMethod.POST)
    @AuthAnnotation(module="目录管理", act=MsgConstant.DIR + MsgConstant.DEL, str="f_dir_del")
    @ResponseBody
    public Object delete(@ModelAttribute("auth") Auth auth,
    					HttpServletRequest request, 
    						 HttpServletResponse response, 
    						 Model model) throws Exception{  
    	authService.delete(auth.getId());
    	return Result.newInstance(CodeConstant.CODE_200, CodeConstant.CODE_SUC);
    }
    
    private List<Ztree> buildZtrees() {
    	List<Auth> auths = authService.getAll();
    	List<Ztree> ztrees = Lists.newArrayList();
    	Ztree ztree = null;
    	String pid = "0";
    	for (Auth auth : auths) {
    		if (!isAuth(auth.getCode(), 6)) {
    			pid = "0";
    			if (isAuth(auth.getCode(), 4)) {
    				pid = auth.getCode().substring(0, 2);
    			}
    			ztrees.add(Ztree.newInstance(auth.getCode(), pid, auth.getName(), auth.getStr(), auth.getUrl()));
    		}
    	}
    	return ztrees;
    }
    
}
