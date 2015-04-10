package com.profit.laji.lang.annotation;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.ElementType;

/**
 * 日志注解
 * @author heyang
 * 2015-02-12
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface LogAnnotation {
	
	/**
	 * 操作对象
	 * @return
	 */
	String module() default "";
	
	/**
	 * 操作权限字符串
	 * @return
	 */
	String authStr() default "";
	
	/**
	 * 操作动作
	 * @return
	 */
	String act() default "";
	
}
