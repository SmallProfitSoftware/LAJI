package com.profit.laji.lang.validator.annotation;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.RUNTIME)
public @interface Date {
	
	String name() default "";

	String format() default "yyyy-MM-dd";
	
	String tips() default "";
	
}
