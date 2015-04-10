package com.profit.laji.lang.validator.annotation;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.RUNTIME)
public @interface NotNull {

	String[] names() default {};
	
	String tips() default "";
	
}
