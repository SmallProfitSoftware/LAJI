package com.profit.laji.lang.validator.annotation;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.RUNTIME)
public @interface DateRange {
	
	String name() default "";
	
	String format() default "yyyy-MM-dd";

	String begin();
	
	String end();
	
	String tips() default "";
}
