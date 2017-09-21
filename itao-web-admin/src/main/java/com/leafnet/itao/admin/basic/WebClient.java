package com.leafnet.itao.admin.basic;

import java.util.Enumeration;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.leafnet.itao.commons.Constants;
import com.leafnet.itao.commons.bean.UserDetail;

/**
 * Web客户端
 * @author Wangd
 * @version 1.0.0
 */
public final class WebClient implements Constants {

	/**
	 * 获取 HttpServletRequest
	 * @return
	 */
	public static HttpServletRequest getRequest(){
		RequestAttributes requestAttributes = RequestContextHolder.getRequestAttributes();
		if (requestAttributes != null)
			return ((ServletRequestAttributes)requestAttributes).getRequest();
		return null;			
	}

	/**
	 * 获取 HttpSession
	 * @return
	 */
	public static HttpSession getSession() {
		return getSession(true);
	}

	public static HttpSession getSession(boolean isCreate) {
		return getRequest() != null ? getRequest().getSession(isCreate) : null;
	}


	/**
	 * 设置为无效
	 * @param request
	 * @return
	 */
	public static void invalidate() {
		HttpSession session = getSession(false);
		if (session != null) {
			session.invalidate();
		}
	}

	/**
	 * 设置属性
	 * @param key
	 * @param value
	 */
	public static void setSessionAttr(String key, Object value) {
		HttpSession session = getSession(true);
		session.setAttribute(key, value);
	}

	/**
	 * 获取属性
	 * @param key
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static <T> T getSessionAttr(String key, Object defaultVal) {
		T val = getSessionAttr(key);
		return (T) (val == null ? defaultVal : val);
	}

	/**
	 * 获取属性
	 * @param key
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static <T> T getSessionAttr(String key) {
		HttpSession session = getSession(false);
		if (session == null)
			return null;
		return (T) session.getAttribute(key);
	}

	/**
	 * 移除属性
	 * @param key
	 * @return
	 */
	public static void delSessionAttr(String key) {
		HttpSession session = getSession(false);
		if (session == null)
			return;
		session.removeAttribute(key);
	}

	/**
	 * 移除Session中所有的 Attribute
	 */
	public static void clearSession() {
		HttpSession session = getSession(false);
		if (session != null) {
			Enumeration<String> attributeNames = session.getAttributeNames();
			while (attributeNames.hasMoreElements()) {
				String attributeName = attributeNames.nextElement();
				session.removeAttribute(attributeName);
			}
		}
	}
	
	/**
	 * 获取用户信息
	 * @param request
	 * @return
	 */
	public static UserDetail getCurUser() {
		return getSessionAttr(CURRENT_USER);
	}
	
	/**
	 * 检测是否存在用户
	 * @param request
	 * @return
	 */
	public static boolean hasUser() {
		return getCurUser() != null;
	}
}
