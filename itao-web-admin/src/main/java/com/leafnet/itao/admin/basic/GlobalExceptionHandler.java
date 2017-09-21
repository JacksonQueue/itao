package com.leafnet.itao.admin.basic;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.exception.ExceptionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.ModelAndView;

import com.leafnet.itao.admin.basic.exception.RedirectViewException;
import com.leafnet.itao.commons.StringHepler;

/**
 * 系统异常处理
 * 
 * @author Wangd
 * @version 1.0.0
 */
@Component
public class GlobalExceptionHandler implements HandlerExceptionResolver {

	protected Logger LOG = LoggerFactory.getLogger(getClass());

	/**
	 * 异常视图名称
	 */
	private String errorViewName;

	/**
	 * 构建剪短的文字信息
	 * 
	 * @param ex
	 * @return
	 */
	public String buildMessageText(Exception ex) {
		if (ex instanceof IllegalArgumentException) {
			LOG.debug("server check fail!", ex);
			return ex.getMessage();
		} else if (ex instanceof IllegalStateException) {
			LOG.debug("server check fail!", ex);
			return ex.getMessage();
		} else if (ex instanceof RedirectViewException) {
			LOG.debug("server check fail!", ex);
			return ex.getMessage();
		}
		LOG.error("server error!", ex);
		return "服务器处理异常!";
	};

	protected boolean isAjaxRequest(HttpServletRequest request) {
		String requestWith = request.getHeader("X-Requested-With");
		return "XMLHttpRequest".equalsIgnoreCase(requestWith);
	}

	@Override
	public ModelAndView resolveException(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) {
		String messageText = buildMessageText(ex);
		String url = "";
		if (ex instanceof RedirectViewException)
			url = ((RedirectViewException) ex).getUrl();

		if (isAjaxRequest(request)) {
			try {
				// 写入异常信息
				messageText = StringHepler.encode(messageText);
				response.setCharacterEncoding("UTF-8");
				response.addHeader("message", messageText);
				response.addHeader("redirectUrl", url);
				response.addHeader("Cache-Control", "no-cache");

				if (StringHepler.isNotBlank(url))
					response.setStatus(501);
				else
					response.setStatus(502);
			} catch (Exception e) {
				LOG.debug("error!", e);
			}
			return new ModelAndView();
		} else {
			ModelAndView modelAndView = new ModelAndView(errorViewName);
			if (StringHepler.isNotBlank(url)) {
				modelAndView.setViewName("redirect:" + url);
			} else {
				modelAndView.addObject("messageText", messageText);
				modelAndView.addObject("ex", ExceptionUtils.getStackTrace(ex));
			}
			return modelAndView;
		}
	}

	public String getErrorViewName() {
		return errorViewName;
	}

	public void setErrorViewName(String errorViewName) {
		this.errorViewName = errorViewName;
	}
}
