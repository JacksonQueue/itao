package com.leafnet.itao.admin.basic;

/**
 * 系统级常用的配置文件
 * @author xiongmw
 * @version 1.0.0
 */
public class SysConf {

	/**
	 * 获取登录地址
	 * @return
	 */
	public static String loginUrl() {
//		return ConfHolder.getString("sys.url.login");
		return "/auth/login.htm";
	}

	/**
	 * 获取登录地址
	 * @return
	 */
	public static String loginCheckUrl() {
		return "/auth/loginCheck.htm";
	}


	/**
	 * 获取首页地址
	 * @return
	 */
	public static String indexUrl() {
		return "/main.htm";
	}

	/**
	 * 系统名称
	 * @return
	 */
	public static String systemName() {
//		return Dict.get("sys.name");
		return null;
	}

	/**
	 * 系统版本
	 * @return
	 */
	public static String systemVersion() {
//		return Dict.get("sys.version");
		return null;
	}
}
