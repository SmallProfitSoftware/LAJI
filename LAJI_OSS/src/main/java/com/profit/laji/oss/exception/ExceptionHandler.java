package com.profit.laji.oss.exception;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

import com.profit.laji.entity.Result;
import com.profit.laji.lang.constant.CodeConstant;
import com.profit.laji.lang.util.JsonUtils;

/**
 * 异常统一处理
 * 
 * @author heyang 2015-03-25
 */
public class ExceptionHandler implements HandlerExceptionResolver {

	public static Logger LOG = LoggerFactory.getLogger(ExceptionHandler.class);

	@Override
	public ModelAndView resolveException(HttpServletRequest request,
			HttpServletResponse response, Object handler, Exception ex) {
		if (((request.getHeader("accept").indexOf("application/json") > -1 || request
				.getHeader("accept").indexOf("application/xml") > -1) || (request
				.getHeader("X-Requested-With") != null && request.getHeader(
				"X-Requested-With").indexOf("XMLHttpRequest") > -1))) {
			try {
				PrintWriter writer = response.getWriter();
				LOG.error(ex.getMessage());
				writer.write(JsonUtils.toJsonString(Result.newInstance(
						CodeConstant.CODE_500, ex.getMessage(),
						CodeConstant.CODE_FAIL)));
				writer.flush();
				return null;
			} catch (IOException e) {
				LOG.error(ex.getMessage());
			}
		}
		Map<String, Object> ERROR = new HashMap<String, Object>();
		ERROR.put("message", ex.getMessage());
		request.getSession().setAttribute("ERROR", ERROR);
		return new ModelAndView(new RedirectView("/errors/500.jsp"));
	}

}
