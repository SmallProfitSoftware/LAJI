package com.profit.laji.lang.validator.annotation;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.RUNTIME)
public @interface Length {
	
	String name() default "";

	int size();
	
	String tips() default "";
	
}
