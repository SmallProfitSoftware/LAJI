package com.profit.laji.lang.validator.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ ElementType.METHOD })
@Retention(RetentionPolicy.RUNTIME)
public @interface ValidatorRule {

	NotNull notNull();
	
	Length[] lengths() default {};
	
	Range[] ranges() default {};
	
	DateRange[] dateRanges() default {};
	
	Date[] dates() default {};
	
	Pattern[] patterns() default {};
	
}
