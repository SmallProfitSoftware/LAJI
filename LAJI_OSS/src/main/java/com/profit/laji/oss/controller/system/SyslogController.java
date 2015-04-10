package com.profit.laji.oss.controller.system;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.profit.laji.lang.annotation.AuthAnnotation;
import com.profit.laji.lang.constant.MsgConstant;
import com.profit.laji.lang.util.StringUtils;
import com.profit.laji.oss.controller.ControllerSupport;

/**
 * 系统日志控制类
 * @author heyang
 * 2015-03-30
 */
@Controller
@SuppressWarnings("all")
@RequestMapping("/views/system")
public class SyslogController extends ControllerSupport {
	
	/** 
     * 日志列表
     */  
    @RequestMapping(value="/syslog_list.json", method = RequestMethod.POST)
    @AuthAnnotation(module="日志管理", act=MsgConstant.LIST, str="d_syslog")
    @ResponseBody
    public Object list(HttpServletRequest request, 
    						 HttpServletResponse response, 
    						 @RequestParam(value="page") int pageNo,
    						 @RequestParam(value="pageSize") int pageSize,
    						 @RequestParam(value="dir", required=false) String dir,
    						 @RequestParam(value="sort", required=false) String sort,
    						 Model model) throws Exception{  
    	String orderBy = "operateDate desc";
		if (!StringUtils.isEmpty(dir))
			orderBy = dir + " " + sort;
    	Map<String, String> params = getReqParams(request, new String[]{"operator", "status"});
    	params.put("orderBy", orderBy);
    	params.put("bNo", ((pageNo - 1) * pageSize) + "");
		params.put("eNo", pageSize + "");
    	return sysLogService.getResults(params);
    }
    
}
