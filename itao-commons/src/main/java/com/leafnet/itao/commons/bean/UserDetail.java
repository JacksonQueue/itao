package com.leafnet.itao.commons.bean;

import java.util.Set;

/**
 * 用户信息
 * 
 * @author Wangd
 * @version 1.0.0
 */
public interface UserDetail {

	/**
	 * 获取用户Id
	 * 
	 * @return
	 */
	String getUserId();

	/**
	 * 获取用户名
	 */
	String getName();

	/**
	 * 获取当前拥有的资源权限
	 * 
	 * @return
	 */
	Set<String> getResourceKeys();

}
