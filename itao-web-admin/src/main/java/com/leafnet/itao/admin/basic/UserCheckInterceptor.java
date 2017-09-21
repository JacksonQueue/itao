package com.leafnet.itao.admin.basic;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.method.HandlerMethod;

import com.leafnet.itao.admin.basic.exception.RedirectViewException;
import com.leafnet.itao.commons.Constants;
import com.leafnet.itao.commons.bean.UserDetail;

/**
 * 用户登录检查
 * @author Wangd
 * @version 1.0.0
 */
@Component
public class UserCheckInterceptor extends AbsHandlerInterceptor implements Constants {

	protected final Logger LOG = LoggerFactory.getLogger(getClass());
	
	protected final static String sys_url_login_check = "/auth/loginCheck.htm";

	/**
	 * 该方法主要对权限进行过滤
	 */
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
		LOG.debug("request url:" + request.getRequestURL());
		
		if (!(handler instanceof HandlerMethod))
			return true;

		HandlerMethod handlerBean = (HandlerMethod) handler;

		// 免校验注解
		if (handlerBean.getMethodAnnotation(UncheckAuth.class) != null)
			return true;
		
        UserDetail curUser = WebClient.getCurUser(); 		
		
        if (curUser == null) {
        	// 直接响应错误
        	ResponseBody responseBody = handlerBean.getMethodAnnotation(ResponseBody.class);
        	if (responseBody != null) 
        		throw new IllegalStateException("您当前登录已失效,请刷新页面重试.");
        	
        	// 当前用户未登陆跳转至登陆页面
			LOG.debug("send redirect to login page!");
			String loginURL = WebHelper.createFullServerUrl(request, sys_url_login_check);
			// 构建回调后的跳转地址
			String nextUrl = WebHelper.generateNextUrl(request);
			loginURL = WebHelper.urlAddParams(loginURL, nextUrl);
			throw new RedirectViewException("用户未登录或已失效.", loginURL);
        }
		
		return true;
	}
}
