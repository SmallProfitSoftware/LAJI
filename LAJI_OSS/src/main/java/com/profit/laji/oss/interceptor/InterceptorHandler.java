package com.profit.laji.oss.interceptor;

import java.util.Arrays;
import java.util.Enumeration;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.shiro.authc.LockedAccountException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import com.profit.laji.entity.Result;
import com.profit.laji.entity.exception.BusinessException;
import com.profit.laji.entity.exception.PermissionDeniedException;
import com.profit.laji.entity.system.Auth;
import com.profit.laji.entity.system.User;
import com.profit.laji.lang.constant.CodeConstant;
import com.profit.laji.lang.constant.MsgConstant;
import com.profit.laji.lang.util.FileUtils;
import com.profit.laji.lang.util.JsonUtils;
import com.profit.laji.lang.util.StringUtils;
import com.profit.laji.oss.controller.ControllerSupport;

/**
 * 公共拦截器
 * 
 * @author heyang 2014-05-26
 */
public class InterceptorHandler implements HandlerInterceptor {
	/**
	 * 日志
	 */
	public static Logger LOG = LoggerFactory.getLogger(InterceptorHandler.class);
	
	/** 
	* 不拦截URL集合
	*/ 
	private List<String> unhandlers = null;
	
	/**
	 * 不拦截url配置文件名
	 */
	public final String UNURL_PROPS = "config/unhandler";

	/**
	 * SQL注入关键字
	 */
	private final String[] keyAttr = { "insert", "select", "delete", "update",
			"count", "*", "%" };
	
	/**
	 * 登录和退出不拦截
	 */
	private final String[] noHandlers = {"/views/sytem/login", "/views/system/logout"};
	
	public InterceptorHandler(){
		try {
			unhandlers = FileUtils.readProps(UNURL_PROPS);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@Override
	public boolean preHandle(HttpServletRequest request,
			HttpServletResponse response, Object handler) throws Exception {
		LOG.debug("公共拦截器进行请求拦截处理...");
		//登录和退出
		User user = (User) request.getSession().getAttribute("user");
		if (user == null){
			throw new BusinessException(MsgConstant.EXCEPTION_NOLOGIN_SESSION_OVERDUE);
		} else if (StringUtils.isTrue(user.getIsLock() + "")){
			throw new LockedAccountException("用户[" + user.getUserName() + "]已锁定！");
		}
		if (!sqlInjection(request, response)) {
			return false;
		}
		return urlIntercept(request, response, user);
	}
	
	/**
	 * sql注入
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	private boolean sqlInjection(HttpServletRequest request, HttpServletResponse response) throws Exception {
		LOG.debug("防SQL注入请求拦截");
		Enumeration<String> names = request.getParameterNames();
		while (names.hasMoreElements()) {
			String name = names.nextElement();
			String[] values = request.getParameterValues(name);
			for (int i = 0, j = values.length; i < j; i ++) {
				if (Arrays.asList(keyAttr).contains(values[i])) {
					response.setHeader("Cache-Control", "no-cache");
					response.setContentType("text/html;charset=UTF-8");
					response.setCharacterEncoding("UTF-8");
					response.getWriter().write(JsonUtils.toJsonString(Result.newInstance(CodeConstant.CODE_500, MsgConstant.MSG_1090, CodeConstant.CODE_FAIL)));
					response.getWriter().flush();
					return false;
				}
			}
		}
		return true;
	}
	
	/**
	 * 权限拦截
	 * @param request
	 * @param response
	 * @return
	 */
	private boolean urlIntercept(HttpServletRequest request, HttpServletResponse response, User user) {
		LOG.debug("URL请求拦截");
		//请求级权限校验
		List<Auth> userAuths = (List<Auth>) request.getSession().getAttribute(ControllerSupport.ALL_AUTHS);
		if (StringUtils.isNull(userAuths) || userAuths.isEmpty())
			throw new BusinessException(MsgConstant.MSG_5000);
		String url = request.getRequestURI();
		LOG.debug("当前URL请求：" + url);
		if(unhandlers.contains(url)) {
			LOG.debug("URL[" + url + "]无须鉴权，允许访问");
			return true;
		}
		Auth auth = null;
		boolean hasAuth = false;
		for (int i = 0, j = userAuths.size(); i < j; i ++){
			auth = userAuths.get(i);
			if (url.equals("/" + auth.getUrl())){
				hasAuth = true;
				break;
			}
		}
		if (!hasAuth){
			LOG.debug("帐号[" + user.getUserName() + "]无法访问[" + url + "]");
			throw new PermissionDeniedException(MsgConstant.MSG_1020);
		}
		LOG.debug("帐号[" + user.getUserName() + "]可以访问[" + url + "]");
		return true;
	}
	
	/**
	 * 是否处理
	 * @param request
	 * @return
	 */
	private boolean isHandler(HttpServletRequest request) {
		String url = request.getRequestURI();
		return Arrays.asList(noHandlers).contains(url);
	}

	@Override
	public void postHandle(HttpServletRequest request,
			HttpServletResponse response, Object handler,
			ModelAndView modelAndView) throws Exception {
		// TODO Auto-generated method stub

	}

	@Override
	public void afterCompletion(HttpServletRequest request,
			HttpServletResponse response, Object handler, Exception ex)
			throws Exception {

	}
}
