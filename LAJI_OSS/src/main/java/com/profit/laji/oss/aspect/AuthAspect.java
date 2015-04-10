package com.profit.laji.oss.aspect;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.profit.laji.entity.exception.BusinessException;
import com.profit.laji.entity.exception.ParameterException;
import com.profit.laji.entity.exception.PermissionDeniedException;
import com.profit.laji.entity.system.Auth;
import com.profit.laji.entity.system.SysLog;
import com.profit.laji.entity.system.User;
import com.profit.laji.lang.annotation.AuthAnnotation;
import com.profit.laji.lang.constant.CodeConstant;
import com.profit.laji.lang.constant.MsgConstant;
import com.profit.laji.lang.util.HttpUtils;
import com.profit.laji.lang.util.StringUtils;
import com.profit.laji.lang.validator.Validator;
import com.profit.laji.lang.validator.annotation.ValidatorRule;
import com.profit.laji.oss.controller.ControllerSupport;

/**
 * 切面:鉴权、校验和记入日志
 * @author heyang 
 * 2015-03-25
 */
@Aspect
@Component
public class AuthAspect extends ControllerSupport {
	
	/**
	 * controller层切入:用于权限校验
	 * @param point
	 * @param validatorRule
	 * @throws Exception
	 */
	@Before("within(com.profit.laji.oss.controller..*) && @annotation(authAnnotation)")
	public void doAspect(JoinPoint point, AuthAnnotation authAnnotation)
			throws Exception {
		HttpServletRequest request = getReq(point);
		//鉴权
		auth(authAnnotation, request);
	}

	/**
	 * controller层切入:用于权限校验和表单验证
	 * @param point
	 * @param validatorRule
	 * @param authAnnotation
	 * @throws Exception
	 */
	@Before("within(com.profit.laji.oss.controller..*) && @annotation(validatorRule) && @annotation(authAnnotation)")
	public void doAspect(JoinPoint point, ValidatorRule validatorRule,
			AuthAnnotation authAnnotation) throws Exception {
		HttpServletRequest request = getReq(point);
		//参数校验
		validate(validatorRule, request);
		//鉴权
		auth(authAnnotation, request);
	}

	/**
	 * controller层切入:记入操作成功日志
	 * @param point
	 * @param validatorRule
	 * @throws Exception
	 */
	@AfterReturning("within(com.profit.laji.oss.controller..*) && @annotation(authAnnotation)")
	public void sucLog(JoinPoint point, AuthAnnotation authAnnotation)
			throws Exception {
		addLog(point, authAnnotation, CodeConstant.CODE_SUC);
	}
	
	/**
	 * controller层切入:记入操作失败日志
	 * @param point
	 * @param validatorRule
	 * @throws Exception
	 */
	@AfterThrowing(pointcut="within(com.profit.laji.oss.controller..*) && @annotation(authAnnotation)") 
	public void failog(JoinPoint point, AuthAnnotation authAnnotation)
			throws Exception {
		addLog(point, authAnnotation, CodeConstant.CODE_FAIL);
	}
	 
	/**
	 * 添加日志
	 * @param point
	 * @param authAnnotation
	 * @param status
	 */
	private void addLog(JoinPoint point, AuthAnnotation authAnnotation, String status) {
		HttpServletRequest request = getReq(point);
		HttpSession session = getSession(request);
    	if (StringUtils.isNull(session))
			throw new BusinessException(MsgConstant.EXCEPTION_NOLOGIN_SESSION_OVERDUE);
    	User user = (User) session.getAttribute("user");
    	/*if (StringUtils.isNull(user))
			throw new BusinessException(MsgConstant.EXCEPTION_NOLOGIN_SESSION_OVERDUE);*/
    	if (!StringUtils.isNull(user)) {
    		if (StringUtils.isNull(authAnnotation))
        		throw new BusinessException(MsgConstant.MSG_1010);
    		String ip = HttpUtils.getClientIP(request);
    		String message = "[" + ip + "]用户[" + user.getUserId() + "]进行了[" + authAnnotation.module() + "-" + authAnnotation.act() + "]";
    		SysLog log = SysLog.newInstance(authAnnotation.module(), authAnnotation.act(), ip, status, message, user.getUserId());
    		sysLogService.add(log);
    	}
	 }
	
	/**
     * 参数校验
     * @param validatorRule
     * @param request
     * @throws Exception 
     */
    private void validate(ValidatorRule validatorRule, HttpServletRequest request) throws Exception {
    	//参数校验
		try {
			Validator.validate(validatorRule, request);
		} catch (ParameterException e) {
			throw new ParameterException(MsgConstant.MSG_1010);
		}
    }
    
    /**
     * 鉴权
     * @param validatorRule
     * @param request
     * @throws Exception
     */
    private void auth(AuthAnnotation authAnnotation, HttpServletRequest request) throws Exception {
    	HttpSession session = getSession(request);
    	if (StringUtils.isNull(session))
			throw new BusinessException(MsgConstant.EXCEPTION_NOLOGIN_SESSION_OVERDUE);
    	if (StringUtils.isNull(authAnnotation))
    		throw new BusinessException(MsgConstant.MSG_1010);
    	//是否需要鉴权
    	if (!authAnnotation.check())
    		return;
    	User user = (User) session.getAttribute("user");
    	if (StringUtils.isNull(user))
			throw new BusinessException(MsgConstant.EXCEPTION_NOLOGIN_SESSION_OVERDUE);
    	//方法级权限校验
    	List<Auth> userAuths = (List<Auth>) request.getSession().getAttribute(ControllerSupport.ALL_AUTHS);
		if (StringUtils.isNull(userAuths) || userAuths.isEmpty())
			throw new BusinessException(MsgConstant.MSG_5000);
		String str = authAnnotation.str();
		Auth auth = null;
		boolean hasAuth = false;
		for (int i = 0, j = userAuths.size(); i < j; i ++){
			auth = userAuths.get(i);
			if (str.equals(auth.getStr())){
				hasAuth = true;
				break;
			}
		}
		if (!hasAuth){
			throw new PermissionDeniedException(MsgConstant.MSG_1020);
		}
    }
	
	/**
     * 获取request
     * @param point
     * @return
     */
    private HttpServletRequest getReq(JoinPoint point) {
		HttpServletRequest request = null;
		Object[] parames = point.getArgs();//获取目标方法体参数  
		Object obj = null;
		for (int i = 0, j = parames.length; i < j; i ++) {
			obj = parames[i];
			if (obj instanceof HttpServletRequest) {
				request = (HttpServletRequest) obj;
			}
		}
		return request;
	}
    
	
}
