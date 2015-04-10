package com.profit.laji.lang.annotation;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.ElementType;

/**
 * 日志注解
 * @author heyang
 * 2014-03-03
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface AuthAnnotation {
	
	/**
	 * 操作对象
	 * @return
	 */
	String module() default "";
	
	/**
	 * 操作动作
	 * @return
	 */
	String act() default "";
	
	/**
	 * 权限字符串
	 * @return
	 */
	String str() default "";
	
	/**
	 * 是否鉴权
	 * @return
	 */
	boolean check() default true;
	
}
