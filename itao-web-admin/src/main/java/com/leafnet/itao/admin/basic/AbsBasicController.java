package com.leafnet.itao.admin.basic;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.ApplicationEvent;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.leafnet.itao.commons.Constants;

/**
 * Title:Controller 基类
 * 
 * @author: Wangd
 * @version: 1.0
 */
public abstract class AbsBasicController implements ApplicationContextAware, Constants {

    protected Logger LOG = LoggerFactory.getLogger(getClass());

    private ApplicationContext applicationContext;

    protected HttpServletRequest httpServletRequest;

    @ModelAttribute
    public void setReqAndRes(HttpServletRequest request, HttpServletResponse response) {
	this.httpServletRequest = request;
    }

    /**
     * 获取请求对象
     * 
     * @return
     */
    public HttpServletRequest getHttpServletRequest() {
	return this.httpServletRequest;
    }

    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
	this.applicationContext = applicationContext;
    }

    /**
     * 抛出事件
     * 
     * @param event
     */
    protected void publishEvent(ApplicationEvent event) {
	applicationContext.publishEvent(event);
    }

    private ServletRequestAttributes getRequestAttributes() {
	return (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
    }

    /**
     * 获取request对象
     * 
     * @return
     */
    public HttpServletRequest getRequest() {
	ServletRequestAttributes webRequest = getRequestAttributes();
	return webRequest == null ? null : webRequest.getRequest();
    }

    /**
     * 获取response对象
     * 
     * @return
     */
    public HttpServletResponse getResponse() {
	ServletRequestAttributes webRequest = getRequestAttributes();
	return webRequest == null ? null : webRequest.getResponse();
    }

    /**
     * 重定向Url
     * 
     * @param url
     */
    public void sendRedirectUrl(String url) {
	try {
	    getResponse().sendRedirect(url);
	} catch (IOException e) {
	    String errorText = "重定向至URL:[%s]异常.";
	    LOG.warn(String.format(errorText, url), e);
	}
    }

    /**
     * 在Response没有提交前发送重定向Url
     * 
     * @param url
     */
    public void sendRedirectUrlAndResponseNotCommitted(String redirectUrl) {
	if (!getResponse().isCommitted())
	    sendRedirectUrl(redirectUrl);
    }

    /**
     * 获取请求参数
     * 
     * @param name
     * @return
     */
    public String param(String name) {
	return getRequest().getParameter(name);
    }

    /**
     * 获取请求参数
     * 
     * @param name
     * @return
     */
    public String[] params(String name) {
	return getRequest().getParameterValues(name);
    }

    /**
     * 获取跳转的Url
     * 
     * @return
     */
    public String getNextUrl() {
	return param(REQ_NEXT_URL);
    }

    /**
     * 获取浏览器标识
     * 
     * @return
     */
    public String getUserAgent() {
	return getRequest().getHeader("User-Agent");
    }

}
