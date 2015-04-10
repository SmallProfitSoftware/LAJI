package com.profit.laji.lang.validator;

import java.lang.reflect.Field;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;

import com.profit.laji.entity.exception.ParameterException;
import com.profit.laji.lang.util.ReflectUtils;
import com.profit.laji.lang.util.StringUtils;
import com.profit.laji.lang.validator.annotation.DateRange;
import com.profit.laji.lang.validator.annotation.Length;
import com.profit.laji.lang.validator.annotation.NotNull;
import com.profit.laji.lang.validator.annotation.Pattern;
import com.profit.laji.lang.validator.annotation.Range;
import com.profit.laji.lang.validator.annotation.ValidatorRule;
import com.profit.laji.lang.validator.constant.Constant;


/**
 * 参数校验
 * @author heyang
 * 2014-12-24
 */
public class Validator {
	
	/**
	 * 日期格式化
	 */
	private static final SimpleDateFormat sdf = new SimpleDateFormat(Constant.DEFAULT_FORMAT);
	
	/**
	 * 参数验证
	 * @param validatorRule
	 * @param request
	 * @throws Exception
	 */
	public static synchronized void validate(ValidatorRule validatorRule, HttpServletRequest request) throws Exception {
		notNull(validatorRule.notNull(), request);
		length(validatorRule.lengths(), request);
		range(validatorRule.ranges(), request);
		date(validatorRule.dates(), request);
		pattern(validatorRule.patterns(), request);
		dateRange(validatorRule.dateRanges(), request);
	}
	
	/**
	 * 实体属性参数校验
	 * @param beans
	 */
	public static void validate(Object ... beans) throws Exception {
		if (beans == null)
			throw new ParameterException("Bean is null.");
		for (Object obj : beans) {
			eachFields(obj);
		}
	}
	
	/**
	 * 遍历属性
	 * @param bean
	 */
	private static void eachFields(Object bean) {
		Field[] fields = bean.getClass().getDeclaredFields();
		for (Field field : fields) {
			annoValidate(bean, field);
		}
	}
	
	/**
	 * 注解校验
	 * @param bean
	 * @param field
	 */
	private static synchronized void annoValidate(Object bean, Field field) {
		NotNull notNull = field.getAnnotation(NotNull.class);
		notNull(bean, notNull, field);
		Length length = field.getAnnotation(Length.class);
		length(bean, length, field);
		Range range = field.getAnnotation(Range.class);
		range(bean, range, field);
		com.profit.laji.lang.validator.annotation.Date date = field.getAnnotation(com.profit.laji.lang.validator.annotation.Date.class);
		date(bean, date, field);
		DateRange dateRange = field.getAnnotation(DateRange.class);
		dateRange(bean, dateRange, field);
		Pattern pattern = field.getAnnotation(Pattern.class);
		pattern(bean, pattern, field);
	}
	
	/**
	 * 非空校验
	 * @param notNull
	 * @param request
	 */
	private static void notNull(NotNull notNull, HttpServletRequest request) {
		if (notNull == null)
			return;
		for (String name : notNull.names()) {
			isNull(request.getParameter(name), notNull);
		}
	}
	
	/**
	 * 非空校验
	 * @param bean
	 * @param notNull
	 * @param field
	 */
	private static void notNull(Object bean, NotNull notNull, Field field) {
		if (notNull == null)
			return;
		Object value = ReflectUtils.getFieldValue(bean, field.getName());
		if (value == null) 
			throw new ParameterException(notNull.tips());
		isNull((String) value, notNull);
	}
	
	/**
	 * 非空校验
	 * @param value
	 * @param tips
	 */
	private static void isNull(String value, NotNull notNull) {
		if (StringUtils.isEmpty(value))
			throw new ParameterException(notNull.tips());
	}
	
	/**
	 * 长度校验
	 * @param lengths
	 * @param request
	 */
	private static void length(Length[] lengths, HttpServletRequest request) {
		if (lengths == null)
			return;
		for (Length length : lengths) {
			isLength(request.getParameter(length.name()), length);
		}
	}
	
	/**
	 * 长度校验
	 * @param bean
	 * @param length
	 * @param field
	 */
	private static void length(Object bean, Length length, Field field) {
		if (length == null)
			return;
		isLength((String) ReflectUtils.getFieldValue(bean, field.getName()), length);
	}
	
	/**
	 * 长度校验
	 * @param value
	 * @param size
	 * @param tips
	 */
	private static void isLength(String value, Length length) {
		if (StringUtils.isEmpty(value) 
				|| value.length() > length.size())
			throw new ParameterException(length.tips());
	}
	
	/**
	 * 范围校验
	 * @param ranges
	 * @param request
	 */
	private static void range(Range[] ranges, HttpServletRequest request) {
		if (ranges == null)
			return;
		for (Range range : ranges) {
			isRange(request.getParameter(range.name()), range);
		}
	}
	
	/**
	 * 范围校验
	 * @param bean
	 * @param range
	 * @param field
	 */
	private static void range(Object bean, Range range, Field field) {
		if (range == null)
			return;
		isRange((String) ReflectUtils.getFieldValue(bean, field.getName()), range);
	}
	
	/**
	 * 范围校验
	 * @param value
	 * @param range
	 */
	private static void isRange(String value, Range range) {
		if (StringUtils.isEmpty(value) 
				|| !value.matches(Constant.NUMBER_REGEX)
				|| Integer.parseInt(value) < range.min()
				|| Integer.parseInt(value) > range.max())
			throw new ParameterException(range.tips());
	}
	
	/**
	 * 日期校验
	 * @param dates
	 * @param request
	 */
	private static void date(com.profit.laji.lang.validator.annotation.Date[] dates, HttpServletRequest request) {
		if (dates == null)
			return;
		for (com.profit.laji.lang.validator.annotation.Date date : dates) {
			isDate(request.getParameter(date.name()), date);
		}
	}
	
	/**
	 * 日期校验
	 * @param dates
	 * @param request
	 */
	private static void date(Object bean, com.profit.laji.lang.validator.annotation.Date date, Field field) {
		if (date == null)
			return;
		isDate((String) ReflectUtils.getFieldValue(bean, field.getName()), date);
	}
	
	/**
	 * 日期校验
	 * @param value
	 * @param date
	 */
	private static void isDate(String value, com.profit.laji.lang.validator.annotation.Date date) {
		isDate(value, date.format(), date.tips());
	}
	
	/**
	 * 日期校验
	 * @param value
	 * @param format
	 * @param tips
	 */
	private static void isDate(String value, String format, String tips) {
		if (StringUtils.isEmpty(format))
			format = Constant.DEFAULT_FORMAT;
		ParsePosition pos = new ParsePosition(0);
		if (StringUtils.isEmpty(value))
			throw new ParameterException(tips);
		sdf.setLenient(false);
		sdf.applyPattern(format);
		Date time = null;
		try {
			time = sdf.parse(value, pos);
		} catch (Exception e) {
			throw new ParameterException(tips);
		}
		if (time == null || pos.getErrorIndex() > 0) {
			throw new ParameterException(tips);
	    }
	    if (pos.getIndex() != value.length()) {
	    	throw new ParameterException(tips);
	    }
	    if (sdf.getCalendar().get(Calendar.YEAR) < 1000 || sdf.getCalendar().get(Calendar.YEAR) > 9999) {
	    	throw new ParameterException(tips);
	    }
	}
	
	/**
	 * 时间范围校验
	 * @param bean
	 * @param range
	 * @param field
	 */
	private static void dateRange(DateRange[] dateRanges, HttpServletRequest request) {
		if (dateRanges == null)
			return;
		for (DateRange dateRange : dateRanges) {
			isDate(request.getParameter(dateRange.name()), dateRange.format(), dateRange.tips());
			isDate(dateRange.begin(), dateRange.format(), dateRange.tips());
			isDate(dateRange.end(), dateRange.format(), dateRange.tips());
			isDateRange(request.getParameter(dateRange.name()), dateRange);
		}
	}
	
	/**
	 * 时间范围校验
	 * @param bean
	 * @param range
	 * @param field
	 */
	private static void dateRange(Object bean, DateRange dateRange, Field field) {
		if (dateRange == null)
			return;
		isDate((String) ReflectUtils.getFieldValue(bean, field.getName()), dateRange.format(), dateRange.tips());
		isDate(dateRange.begin(), dateRange.format(), dateRange.tips());
		isDate(dateRange.end(), dateRange.format(), dateRange.tips());
		isDateRange((String) ReflectUtils.getFieldValue(bean, field.getName()), dateRange);
	}
	
	/**
	 * 范围校验
	 * @param value
	 * @param range
	 */
	private static void isDateRange(String value, DateRange dateRange) {
		String format = dateRange.format();
		if (StringUtils.isEmpty(format))
			format = Constant.DEFAULT_FORMAT;
		sdf.setLenient(false);
		sdf.applyPattern(format);
		Date now = null, begin = null, end = null;
		try {
			now = sdf.parse(value);
			begin = sdf.parse(dateRange.begin());
			end = sdf.parse(dateRange.end());
		} catch (Exception e) {
			throw new ParameterException(dateRange.tips());
		}
		if (now.before(begin) || now.after(end))
			throw new ParameterException(dateRange.tips());
	}
	
	/**
	 * 正则校验
	 * @param patterns
	 * @param request
	 */
	private static void pattern(Pattern[] patterns, HttpServletRequest request) {
		if (patterns == null)
			return;
		for (Pattern pattern : patterns) {
			isPattern(request.getParameter(pattern.name()), pattern);
		}
	}
	
	/**
	 * 正则校验
	 * @param bean
	 * @param pattern
	 * @param field
	 */
	private static void pattern(Object bean, Pattern pattern, Field field) {
		if (pattern == null)
			return;
		isPattern((String) ReflectUtils.getFieldValue(bean, field.getName()), pattern);
	}
	
	/**
	 * 正则校验
	 * @param value
	 * @param pattern
	 */
	private static void isPattern(String value, Pattern pattern) {
		if (StringUtils.isEmpty(value) 
				|| !value.matches(pattern.regex()))
			throw new ParameterException(pattern.tips());
	}
	
}
