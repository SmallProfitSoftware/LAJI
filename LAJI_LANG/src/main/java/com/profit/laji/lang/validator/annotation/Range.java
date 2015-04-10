package com.profit.laji.lang.validator.annotation;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.RUNTIME)
public @interface Range {
	
	String name() default "";

	int min();
	
	int max();
	
	String tips() default "";
}
