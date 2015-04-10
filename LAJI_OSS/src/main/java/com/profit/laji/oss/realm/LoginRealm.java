package com.profit.laji.oss.realm;

import java.util.Map;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.LockedAccountException;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.subject.Subject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.google.common.collect.Maps;
import com.profit.laji.entity.system.User;
import com.profit.laji.lang.util.StringUtils;
import com.profit.laji.oss.service.system.UserService;


/**
 * 登陆认证
 * @author heyang
 * 2015-03-25
 */
public class LoginRealm extends AuthorizingRealm {
	
	public static Logger LOG = LoggerFactory.getLogger(LoginRealm.class);
	
	@Autowired
	private UserService userService;

	@Override
	protected AuthorizationInfo doGetAuthorizationInfo(
			PrincipalCollection principals) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	protected AuthenticationInfo doGetAuthenticationInfo(
			AuthenticationToken authcToken) throws AuthenticationException {  
		UsernamePasswordToken token = (UsernamePasswordToken) authcToken;
		String userId = token.getUsername();
		User user = userService.get(userId);
		if(StringUtils.isNull(user)){
			throw new UnknownAccountException("帐号[" + userId + "]不存在！");
		} else if (StringUtils.isTrue(user.getIsLock() + "")) {
			throw new LockedAccountException("帐号[" + userId  + "]已锁定！");
		}
		String userid = user.getUserId();
		String md5Pwd = user.getUserPwd();
		SimpleAuthenticationInfo sai = new SimpleAuthenticationInfo(userid, md5Pwd, getName());
		 //用户登陆成功，将用户放入Session中
		user.setUserPwd(null);
		setSession("user", user);
		return sai;
    }
	
	/** 
     * 将一些数据放到ShiroSession中,以便于其它地方使用 
     * @see 比如Controller,使用时直接用HttpSession.getAttribute(key)就可以取到 
     */  
    private void setSession(Object key, Object value){  
        Session session = getShiroSession();  
        LOG.debug("session默认超时时间为[" + session.getTimeout() + "]毫秒");  
        if(null != session){  
            session.setAttribute(key, value);  
        }  
    }  
    
    /**
     * 获取会话
     * @return
     */
    private Session getShiroSession() {
    	Subject currentUser = SecurityUtils.getSubject();  
    	return currentUser.getSession();
    }
	
}
